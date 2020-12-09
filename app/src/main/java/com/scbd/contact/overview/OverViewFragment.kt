package com.scbd.contact.overview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.scbd.contact.databinding.FragmentOverViewBinding

private const val REQUEST_READ_CONTACTS = 101

class OverViewFragment : Fragment() {
    var isPermissionGranted = MutableLiveData<Boolean>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentOverViewBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val snackBar = Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            "Give Permission to Read Contacts",
            Snackbar.LENGTH_INDEFINITE
        )
        isPermissionGranted.value = false

        isPermissionGranted.observe(viewLifecycleOwner, Observer { hasPermision ->
            if (hasPermision) {
                val viewModelFactory = OverViewViewModelFactory(application)
                val viewModel =
                    ViewModelProvider(this, viewModelFactory).get(OverViewViewModel::class.java)
                binding.viewModel = viewModel
                snackBar.dismiss()

                viewModel.navigateToSelectedContact.observe(viewLifecycleOwner, Observer {
                    if (null != it) {
                        this.findNavController().navigate(
                            OverViewFragmentDirections.actionOverViewFragmentToDetailFragment(it)
                        )
                        viewModel.displayUserDetailsComplete()
                    }

                })

                binding.recyclerView.adapter =
                    ContactListAdapter(OnClickListener { viewModel.displayContactDetails(it) })

            } else {
                snackBar.show()
            }
        })
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        if (!isPermissionGranted.value!!) {
            checkContactPermission()
        }
    }

    private fun checkContactPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            isPermissionGranted.value = true
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.READ_CONTACTS),
            REQUEST_READ_CONTACTS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_CONTACTS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isPermissionGranted.value = true
                }
            }
        }
    }

}