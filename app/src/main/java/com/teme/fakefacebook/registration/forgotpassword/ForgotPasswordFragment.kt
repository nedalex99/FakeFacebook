package com.teme.fakefacebook.registration.forgotpassword

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.teme.fakefacebook.R
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        focusEmailEditText()

        setupUI()
    }

    private fun setupUI() {
        find_acc_btn.setOnClickListener {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email_et.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Password reset email sent!", Toast.LENGTH_LONG)
                            .show()
                        view?.findNavController()
                            ?.navigate(R.id.action_forgotPasswordFragment_to_signInFragment)
                    } else {
                        Log.d("Password reset", it.exception.toString())
                    }
                }
        }
    }

    private fun focusEmailEditText() {
        val imgr = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        imgr.showSoftInput(email_et, 0)
        email_et.requestFocus()
    }
}