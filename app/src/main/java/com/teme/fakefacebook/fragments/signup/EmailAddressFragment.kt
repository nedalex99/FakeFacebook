package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.teme.fakefacebook.R
import kotlinx.android.synthetic.main.fragment_email_address.*
import kotlinx.android.synthetic.main.fragment_email_address.back_img
import kotlinx.android.synthetic.main.fragment_email_address.next_btn

class EmailAddressFragment : Fragment() {

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var day: String
    private lateinit var month: String
    private lateinit var year: String
    private lateinit var gender: String
    private lateinit var mobile: String
    private lateinit var password: String

    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserId()

        setupUI()
    }


    private fun setupUI() {
        next_btn.setOnClickListener {
            //userId?.let { it1 -> addEmailAddressToUser(it1) }
            goToTermsFragment()
        }

        skip_tv.setOnClickListener {
            //userId?.let { it1 -> addEmailAddressToUser(it1) }
            goToTermsFragment()
        }

        back_img.setOnClickListener {
            createAlertDialog()
        }
    }

    private fun goToTermsFragment() {
        val action =
            EmailAddressFragmentDirections.actionEmailAddressFragmentToTermsPrivacyFragment(
                firstName = firstName,
                lastName = lastName,
                day = day,
                month = month,
                year = year,
                gender = gender,
                mobile = mobile,
                password = password,
                email = email_et.text.toString()
            )
        action.let { it1 ->
            view?.findNavController()
                ?.navigate(it1)
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
                    ?.navigate(R.id.action_emailAddressFragment_to_signInFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }

    private fun addEmailAddressToUser(userId: String) {
        userId.let {
            FirebaseFirestore.getInstance().collection("users").document(it)
                .update("email", email_et.text.toString())
        }
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
            val args = EmailAddressFragmentArgs.fromBundle(requireArguments())
            this.firstName = args.firstName
            this.lastName = args.lastName
            this.day = args.day
            this.month = args.month
            this.year = args.year
            this.gender = args.gender
            this.mobile = args.mobile
            this.password = args.password.toString()
        }
        return null
    }
}