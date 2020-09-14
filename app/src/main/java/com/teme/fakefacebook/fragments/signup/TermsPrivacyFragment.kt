package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
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
            goToCreateAccSplashFragment()
        }
    }

    private fun goToCreateAccSplashFragment() {
        val action = user?.let {
            TermsPrivacyFragmentDirections
                .actionTermsPrivacyFragmentToCreateAccSplashFragment(user = it)
        }
        action?.let { view?.findNavController()?.navigate(it) }
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