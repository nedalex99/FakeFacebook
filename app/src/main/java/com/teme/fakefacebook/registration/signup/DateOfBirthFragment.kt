package com.teme.fakefacebook.registration.signup

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.teme.fakefacebook.R
import com.teme.fakefacebook.registration.models.User
import kotlinx.android.synthetic.main.fragment_date_of_birth.*
import kotlinx.android.synthetic.main.fragment_date_of_birth.back_img
import kotlinx.android.synthetic.main.fragment_date_of_birth.error
import java.time.LocalDateTime


class DateOfBirthFragment : Fragment() {

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_of_birth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getUser()

        setupUI()
    }

    private fun setupUI() {

        choose_date_btn.setOnClickListener {
            if (!checkYearChosen()) {
                showError()
            } else {
                user?.day = date_picker.dayOfMonth.toString()
                user?.month = date_picker.month.toString()
                user?.year = date_picker.year.toString()
                hideError()
                goToGenderFragment()
            }
        }

        back_img.setOnClickListener {
            createAlertDialog()
        }
    }

    private fun checkYearChosen(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDate = LocalDateTime.now()
            if (currentDate.year - date_picker.year < 5) {
                return false
            }
        }
        return true
    }

    private fun goToGenderFragment() {
        val action = user?.let {
            DateOfBirthFragmentDirections.actionDateOfBirthFragmentToGenderFragment(user = it)
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
                    ?.navigate(R.id.action_dateOfBirthFragment_to_signInFragment)
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
            val args = DateOfBirthFragmentArgs.fromBundle(requireArguments())
            return args.user

        }
        return null
    }
}