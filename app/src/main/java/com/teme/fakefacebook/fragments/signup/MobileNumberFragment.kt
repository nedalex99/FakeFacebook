package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.teme.fakefacebook.R
import com.teme.fakefacebook.models.User
import kotlinx.android.synthetic.main.fragment_mobile_number.*
import kotlinx.android.synthetic.main.fragment_mobile_number.back_img
import kotlinx.android.synthetic.main.fragment_mobile_number.next_btn

class MobileNumberFragment : Fragment() {

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mobile_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getUser()

        setupUI()

        mobileNumberTextChanged()
    }

    private fun mobileNumberTextChanged() {
        mobile_number_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (s.isNotEmpty()) {
                        hideError()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setupUI() {
        next_btn.setOnClickListener {
            if (mobile_number_et.text.isEmpty()) {
                showError()
            } else {
                user?.mobileNumber = ccp.selectedCountryCodeWithPlus + mobile_number_et.text.toString()
                goToPasswordFragment()
            }
        }

        sign_up_email_tv.setOnClickListener {
            user?.mobileNumber = mobile_number_et.text.toString()
            goToEmailFragment()
        }

        back_img.setOnClickListener {
            createAlertDialog()
        }
    }

    private fun goToPasswordFragment() {
        val action =
            user?.let {
                MobileNumberFragmentDirections.actionMobileNumberFragmentToPasswordFragment(
                    user = it
                )
            }
        action?.let { view?.findNavController()?.navigate(it) }
    }

    private fun goToEmailFragment() {
        val action =
            user?.let {
                MobileNumberFragmentDirections.actionMobileNumberFragmentToEmailAddressFragment(
                    user = it
                )
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
                    ?.navigate(R.id.action_mobileNumberFragment_to_signInFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }

    private fun showError() {
        error.error = true.toString()
        error.visibility = View.VISIBLE
    }

    private fun hideError() {

        error.error = null
        error.visibility = View.GONE
    }

    private fun getUser(): User? {
        arguments?.let {
            val args = MobileNumberFragmentArgs.fromBundle(requireArguments())
            return args.user
        }
        return null
    }

}