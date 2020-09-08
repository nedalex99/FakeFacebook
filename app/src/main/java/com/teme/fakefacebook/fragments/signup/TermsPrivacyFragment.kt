package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.teme.fakefacebook.R
import com.teme.fakefacebook.models.User
import kotlinx.android.synthetic.main.fragment_email_address.back_img
import kotlinx.android.synthetic.main.fragment_terms_privacy.*

class TermsPrivacyFragment : Fragment() {

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var day: String
    private lateinit var month: String
    private lateinit var year: String
    private lateinit var gender: String
    private lateinit var mobile: String
    private lateinit var email: String
    private lateinit var password: String

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

        getUserId()

        setupUI()
    }

    private fun setupUI() {
        back_img.setOnClickListener {
            createAlertDialog()
        }

        sign_up_btn.setOnClickListener {
            progress_horizontal.visibility = View.VISIBLE
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        val user = User(
                            firstName = firstName,
                            lastName = lastName,
                            year = year,
                            month = month,
                            day = day,
                            gender = gender,
                            mobileNumber = mobile,
                            email = email,
                            password = password
                        )

                        val userId = FirebaseAuth.getInstance().currentUser?.uid
                        userId?.let { it1 ->
                            FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(it1)
                                .set(user)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        progress_horizontal.visibility = View.GONE
                                    }
                                }
                        }
                    } else {
                        Toast.makeText(context, p0.exception.toString(), Toast.LENGTH_LONG).show()
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
        }
    }

    private fun getUserId(): String? {
        arguments?.let {
            val args = TermsPrivacyFragmentArgs.fromBundle(requireArguments())
            this.firstName = args.firstName
            this.lastName = args.lastName
            this.day = args.day
            this.month = args.month
            this.year = args.year
            this.gender = args.gender
            this.mobile = args.mobile
            this.email = args.email.toString()
            this.password = args.password
        }
        return null
    }
}