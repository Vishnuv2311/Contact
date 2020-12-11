package com.scbd.contact.details

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.scbd.contact.R
import com.scbd.contact.database.Contact
import com.scbd.contact.database.ContactDataBase
import com.scbd.contact.databinding.FragmentDetailBinding
import com.scbd.contact.utils.ImageBitmapString.bitMapToString

private const val REQUEST_CODE = 0
private const val REQUEST_READ_STORAGE = 101

class DetailFragment : Fragment() {

    lateinit var detailViewModel: DetailViewModel
    lateinit var binding: FragmentDetailBinding
    lateinit var contact: Contact
    var imageString: String = ""
    private var isPermissionGranted = false
    private var isPermissionRequestBlocked = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        contact = DetailFragmentArgs.fromBundle(requireArguments()).contactDetails
        val dataScope = ContactDataBase.getInstance(application).contactDao
        val viewModelFactory = DetailViewModelFactory(dataScope, contact)
        detailViewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewModel = detailViewModel
        binding.lifecycleOwner = this

        binding.contactImg.setOnClickListener {
            if (isPermissionGranted) loadImageFromGallery()
            else checkStoragePermission()
        }

        detailViewModel.imageBitmap.observe(viewLifecycleOwner, {
            it?.let {
                binding.contactImg.setImage(it)
                Thread {
                    val imgStr = bitMapToString(it)
                    if (null != imgStr) imageString = imgStr
                }.start()
            }
        })

        detailViewModel.navigateToOverride.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(DetailFragmentDirections.actionDetailFragmentToOverViewFragment())
                detailViewModel.navigateToOverViewComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        if (contact.name == "") menu.findItem(R.id.update).isVisible = false
        else menu.findItem(R.id.save).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> saveContact()
            R.id.update -> updateContact(contact)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showError(): Boolean {
        binding.nameField.error = null
        binding.numberField.error = null
        binding.emailField.error = null
        if (binding.contactName.text.toString().length < 3) {
            binding.nameField.error = "Name Required"
            return true
        }
        if (binding.contactNumber.text.toString().length < 10) {
            binding.numberField.error = "Phone Required"
            return true
        }
        if (!binding.contactEmail.text.toString().contains("@")) {
            binding.emailField.error = "Email Required"
            return true
        }
        return false
    }

    private fun updateContact(contact: Contact) {
        if (!showError()) {
            detailViewModel.saveContact(createContact(contact.contactId))
            detailViewModel.navigateToOverview()
        }
    }

    private fun saveContact() {
        if (!showError()) {
            detailViewModel.saveContact(createContact(0L))
            detailViewModel.navigateToOverview()
        }
    }

    private fun createContact(contactId: Long) = Contact(
        contactId, binding.contactName.text.toString(),
        binding.contactNumber.text.toString(), imageString,
        binding.contactEmail.text.toString()
    )

    private fun loadImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE);
    }

    private fun checkStoragePermission() {

        when {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> isPermissionGranted = true

            !isPermissionGranted -> {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_STORAGE
                )
            }

            !ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> openSettings()
        }
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", requireContext().packageName, null)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && null != data) {
            try {
                val imageUri = data.data
                if (null != imageUri) {
                    val inputStream = requireContext().contentResolver.openInputStream(imageUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    if (bitmap !== null) detailViewModel.updateImage(bitmap)
                }
            } catch (e: Exception) {
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_STORAGE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) isPermissionGranted =
                true
        }
    }
}