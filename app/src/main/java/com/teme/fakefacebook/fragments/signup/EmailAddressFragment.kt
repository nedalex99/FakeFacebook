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
import com.teme.fakefacebook.models.User
import kotlinx.android.synthetic.main.fragment_email_address.*
import kotlinx.android.synthetic.main.fragment_email_address.back_img
import kotlinx.android.synthetic.main.fragment_email_address.next_btn

class EmailAddressFragment : Fragment() {

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getUser()

        setupUI()
    }


    private fun setupUI() {

        next_btn.setOnClickListener {
            user?.email = email_et.text.toString()
            goToTermsFragment()
        }

        skip_tv.setOnClickListener {
            goToTermsFragment()
        }

        back_img.setOnClickListener {
            createAlertDialog()
        }
    }

    private fun goToTermsFragment() {
        val action =
            user?.let {
                EmailAddressFragmentDirections.actionEmailAddressFragmentToTermsPrivacyFragment(
                    user = it
                )
            }
        action?.let { it1 ->
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
                view?.findNavController()
                    ?.navigate(R.id.action_emailAddressFragment_to_signInFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }


    private fun getUser(): User? {
        arguments?.let {
            val args = EmailAddressFragmentArgs.fromBundle(requireArguments())
            return args.user
        }
        return null
    }
}