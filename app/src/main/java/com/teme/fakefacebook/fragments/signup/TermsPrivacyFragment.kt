package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.teme.fakefacebook.R
import kotlinx.android.synthetic.main.fragment_email_address.*
import kotlinx.android.synthetic.main.fragment_email_address.back_img
import kotlinx.android.synthetic.main.fragment_password.*
import kotlinx.android.synthetic.main.fragment_terms_privacy.*

class TermsPrivacyFragment : Fragment() {

    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_terms_privacy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = getUserId()

        setupUI()
    }

    private fun setupUI() {
        back_img.setOnClickListener {
            createAlertDialog()
        }

        sign_up_btn.setOnClickListener {
            val email = email_et.text.toString()
            val pass = password_et.text.toString()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_et.text.toString(), password_et.text.toString())
        }
    }

    private fun createAlertDialog() {
        activity?.let { it1 -> AlertDialog.Builder(it1) }?.apply {
            setTitle("Do you want to stop creating your account?")
            setMessage("If you stop nou, you'll lose any\n progress you've made.")
            setPositiveButton(
                "Stop Creating Account"
            ) { _, _ ->
                if (userId != null) {
                    deleteUser(userId)
                }
                view?.findNavController()
                    ?.navigate(R.id.action_termsPrivacyFragment_to_signInFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }

    private fun deleteUser(userId: String?) {
        userId?.let {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(it)
                .delete()
        }    }

    private fun getUserId(): String? {
        arguments?.let {
            val args = TermsPrivacyFragmentArgs.fromBundle(requireArguments())
            return args.userId
        }
        return null
    }
}