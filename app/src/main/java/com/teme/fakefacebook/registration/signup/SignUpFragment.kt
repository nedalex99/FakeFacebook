package com.teme.fakefacebook.registration.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.teme.fakefacebook.R
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next_btn.setOnClickListener {
            view.findNavController().navigate(R.id.action_signUpFragment_to_firstLastNameFragment)
        }

        back_img.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_signUpFragment_to_quitRegistrationDialogFragment)
        }
    }

}