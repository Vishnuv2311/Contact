package com.scbd.contact.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.scbd.contact.database.Contact
import com.scbd.contact.database.ContactDataBase
import com.scbd.contact.databinding.FragmentOverViewBinding

class OverViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentOverViewBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val dataSource = ContactDataBase.getInstance(application).contactDao
        val viewModelFactory = OverViewViewModelFactory(application, dataSource)
        val overViewViewModel =
            ViewModelProvider(this, viewModelFactory).get(OverViewViewModel::class.java)

        val emptyContact = Contact(0, "", "", "","")

        overViewViewModel.navigateToAddContact.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(
                        OverViewFragmentDirections.actionOverViewFragmentToDetailFragment(
                            emptyContact
                        )
                    )
                overViewViewModel.navigationComplete()
            }
        })

        overViewViewModel.navigateToSelectedContact.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(OverViewFragmentDirections.actionOverViewFragmentToDetailFragment(it))
                overViewViewModel.displayUserDetailsComplete()
            }
        })

        overViewViewModel.contacts.observe(viewLifecycleOwner, { contactList ->
            if (contactList.isEmpty()) overViewViewModel.showTextMessage(true)
            else overViewViewModel.showTextMessage(false)

        })



        binding.addContact.setOnClickListener {
            overViewViewModel.displayAddContact()
        }

        binding.recyclerView.adapter = ContactListAdapter(OnClickListener { contact ->
            overViewViewModel.navigateToDetails(contact)
            overViewViewModel.navigationComplete()
        })
        binding.viewModel = overViewViewModel
        binding.lifecycleOwner = this

        return binding.root

    }


}