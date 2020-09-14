package com.teme.fakefacebook.registration.signup

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.teme.fakefacebook.R
import com.teme.fakefacebook.registration.models.User
import kotlinx.android.synthetic.main.fragment_account_confirmation.*
import java.util.concurrent.TimeUnit


class AccountConfirmationFragment : Fragment() {

    private lateinit var code: String
    private var user: User? = null
    private var smsCode: String? = null

    private lateinit var newPhoneNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getUser()

        createPhoneAuth()

        setupUI()
    }

    private fun setupUI() {
        confirm_button.setOnClickListener {
            smsCode?.let { it1 ->
                verifyCode(it1)
            }
        }

        send_sms_again_tv.setOnClickListener {
            createPhoneAuth()
            smsCode?.let { it1 -> verifyCode(it1) }
        }

        change_phone_tv.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Change Phone Number")
            dialog.setMessage("Are you sure you want to change the pohne number?")

            val phoneNumber = EditText(context)
            phoneNumber.inputType = InputType.TYPE_CLASS_NUMBER

            dialog.setView(phoneNumber)

            dialog.setPositiveButton("Ok") { _, _ ->
                newPhoneNumber
            }
        }

        confirm_by_email_tv.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
        }
    }

    private fun createPhoneAuth() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            user?.mobileNumber.toString(),
            60,
            TimeUnit.SECONDS,
            requireActivity(),
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    //addPhoneCredential(p0)
                    smsCode = p0.smsCode
                    //p0.smsCode?.let { verifyCode(it) }
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Log.i("Verification failed", p0.message.toString())
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    //goToAccountConfirmationFragment(p0)
                    Log.i("Code sent", p0)
                    code = p0
                }

            }
        )
    }

    private fun verifyCode(codeByUser: String) {
        val credential = PhoneAuthProvider.getCredential(code, codeByUser)

        addPhoneCredential(credential)
    }

    private fun addPhoneCredential(phoneCredential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().currentUser
            ?.updatePhoneNumber(phoneCredential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Phone verified successfully", Toast.LENGTH_LONG).show()
                    view?.findNavController()?.navigate(R.id.action_accountConfirmationFragment_to_signInFragment)
                    //Log.i("Task", "Successful")
                    //confirmation_code_et.setText(smsCode)
                }
            }
    }

    private fun getUser(): User? {
        arguments?.let {
            val args = AccountConfirmationFragmentArgs.fromBundle(requireArguments())
            return args.user
        }
        return null
    }
}