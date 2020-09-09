package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.teme.fakefacebook.R
import com.teme.fakefacebook.models.User
import kotlinx.android.synthetic.main.fragment_email_address.back_img
import kotlinx.android.synthetic.main.fragment_terms_privacy.*

class TermsPrivacyFragment : Fragment() {

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_terms_privacy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getUser()

        setupUI()
    }

    private fun setupUI() {
        back_img.setOnClickListener {
            createAlertDialog()
        }

        sign_up_btn.setOnClickListener {
            progress_horizontal.visibility = View.VISIBLE
            val email = user?.email.toString()
            val password = user?.password.toString()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener { createAccTask ->
                if (createAccTask.isSuccessful) {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    userId?.let { it1 ->
                        user?.let { it2 ->
                            FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(it1)
                                .set(it2)
                                .addOnCompleteListener {
                                    progress_horizontal.visibility = View.GONE
                                }
                        }
                    }
                } else {
                    Toast.makeText(context, createAccTask.exception.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun createAlertDialog() {
        activity?.let { it1 -> AlertDialog.Builder(it1) }?.apply {
            setTitle("Do you want to stop creating your account?")
            setMessage("If you stop nou, you'll lose any\n progress you've made.")
            setPositiveButton(
                "Stop Creating Account"
            ) { _, _ ->
                view?.findNavController()
                    ?.navigate(R.id.action_termsPrivacyFragment_to_signInFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }

    private fun getUser(): User? {
        arguments?.let {
            val args = TermsPrivacyFragmentArgs.fromBundle(requireArguments())
            return args.user
        }
        return null
    }
}