package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.teme.fakefacebook.R
import kotlinx.android.synthetic.main.fragment_email_address.*

class EmailAddressFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next_btn.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_emailAddressFragment_to_termsPrivacyFragment)
        }

        skip_tv.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_emailAddressFragment_to_termsPrivacyFragment)
        }
    }
}