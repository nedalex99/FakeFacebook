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
import kotlinx.android.synthetic.main.fragment_password.*
import kotlinx.android.synthetic.main.fragment_password.back_img
import kotlinx.android.synthetic.main.fragment_password.error
import kotlinx.android.synthetic.main.fragment_password.next_btn


class PasswordFragment : Fragment() {

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getUser()

        setupUI()
    }

    private fun setupUI() {
        next_btn.setOnClickListener {
            if (password_et.text.isEmpty()) {
                showError()
            } else if (checkPassword()) {
                hideError()
                user?.password = password_et.text.toString()
                goToEmailFragment()
            } else {
                showError()
            }
        }

        back_img.setOnClickListener {
            createAlertDialog()
        }
    }

    private fun checkPassword(): Boolean {
        val passwordPattern = Regex(
            "^(?=.*\\d)" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[!@#\$%^&*_+=?;<>,.])" +
                    "(?=\\S+$)" +
                    ".{6,14}\$"
        )

        if (passwordPattern.matches(password_et.text)) {
            return true
        }

        return false

    }

    private fun goToEmailFragment() {
        val action =
            user?.let {
                PasswordFragmentDirections.actionPasswordFragmentToEmailAddressFragment(
                    user = it
                )
            }

        action?.let { it1 ->
            view?.findNavController()?.navigate(it1)
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
                    ?.navigate(R.id.action_passwordFragment_to_signInFragment)
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
            val args = PasswordFragmentArgs.fromBundle(requireArguments())
            return args.user
        }
        return null
    }

}