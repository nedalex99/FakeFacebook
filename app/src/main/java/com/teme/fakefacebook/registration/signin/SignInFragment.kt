package com.teme.fakefacebook.registration.signin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.teme.fakefacebook.R
import com.teme.fakefacebook.dashboard.activities.DashboardActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment() {

    companion object {
        private const val RC_SIGN_IN = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sign_in_btn.setOnClickListener {
            signIn()
        }

        forgot_pass_tv.setOnClickListener {
            view.findNavController().navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
        }

        create_new_acc_btn.setOnClickListener {
            view.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        checkPasswordTextChanged()
        checkShowHidePassImageChanged()
    }

    private fun signIn() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email_et.text.toString(),
            password_et.text.toString()
        ).addOnCompleteListener {
            if(it.isSuccessful){
                if (FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
                    goToDashboardActivity()
                } else {
                    Toast.makeText(context, "Please verify your email!", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun goToDashboardActivity() {
        view?.findNavController()?.navigate(R.id.action_signInFragment_to_dashboardActivity)
    }

    private fun checkPasswordTextChanged() {
        password_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.isNotEmpty()) {
                        show_hide_pass_img.visibility = View.VISIBLE
                    } else {
                        show_hide_pass_img.visibility = View.INVISIBLE
                        show_hide_pass_img.isSelected = false
                        password_et.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun checkShowHidePassImageChanged() {
        show_hide_pass_img.setOnClickListener {
            show_hide_pass_img.isSelected = !show_hide_pass_img.isSelected

            if (show_hide_pass_img.isSelected) {
                password_et.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                password_et.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }

}