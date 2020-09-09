package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.teme.fakefacebook.R
import com.teme.fakefacebook.models.User
import kotlinx.android.synthetic.main.fragment_account_confirmation.*


class AccountConfirmationFragment : Fragment() {

    private var user: User? = null
    private var smsCode: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = FirebaseAuth.getInstance().currentUser?.uid.toString()

        user = getUser()
        smsCode = getSmsCode()

        setupUI()
    }

    private fun setupUI() {
        send_sms_again_tv.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.phoneNumber
        }



        confirm_by_email_tv.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
        }
    }

    private fun getUser(): User? {
        arguments?.let {
            val args = AccountConfirmationFragmentArgs.fromBundle(requireArguments())
            return args.user
        }
        return null
    }

    private fun getSmsCode(): String? {
        arguments?.let {
            val args = AccountConfirmationFragmentArgs.fromBundle(requireArguments())
            return args.code
        }
        return null
    }
}