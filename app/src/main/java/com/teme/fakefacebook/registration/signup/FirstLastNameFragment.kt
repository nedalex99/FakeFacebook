package com.teme.fakefacebook.registration.signup

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
import com.teme.fakefacebook.dialogs.QuitRegistrationDialogFragment
import com.teme.fakefacebook.registration.models.User
import kotlinx.android.synthetic.main.fragment_first_last_name.*

class FirstLastNameFragment : Fragment() {

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_last_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        firstNameTextChanged()
        lastNameTextChanged()
    }

    private fun setupUI() {

        next_btn.setOnClickListener {
            user = User(
                firstName = first_name_et.text.toString(),
                lastName = last_name_et.text.toString()
            )
            nextBtnClick()
        }

        back_img.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_firstLastNameFragment_to_quitRegistrationDialogFragment)
        }
    }

    private fun nextBtnClick() {
        when {
            first_name_et.text!!.isEmpty() && last_name_et.text!!.isEmpty() -> {
                showError("Your name is not correct")
            }
            checkName(last_name_et.text.toString()) && checkName(first_name_et.text.toString()) -> {
                goToDateOfBirthFragment()
                hideError()
            }
            !checkName(last_name_et.text.toString()) && !checkName(first_name_et.text.toString()) -> {
                showError("Your name is not correct")
            }
            !checkName(last_name_et.text.toString()) -> {
                showError("Your name is not correct")
            }
            !checkName(first_name_et.text.toString()) -> {
                showError("Your name is not correct")
            }
        }
    }

    private fun firstNameTextChanged() {
        first_name_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (s.isNotEmpty()) {
                        if (!checkName(s.toString())) {
                            showError("Your name is not correct")
                        } else {
                            hideError()
                        }
                    } else {
                        hideError()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun lastNameTextChanged() {
        last_name_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (s.isNotEmpty()) {
                        if (!checkName(s.toString())) {
                            showError("Your name is not correct")
                        } else {
                            hideError()
                        }
                    } else {
                        hideError()
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun goToDateOfBirthFragment() {
        val action =
            FirstLastNameFragmentDirections.actionFirstLastNameFragmentToDateOfBirthFragment(user)
        view?.findNavController()
            ?.navigate(action)

    }


    private fun showError(errorMessage: String) {
        error.text = errorMessage
        error.error = true.toString()
        error.visibility = View.VISIBLE
    }

    private fun hideError() {
        error.error = null
        error.visibility = View.GONE
    }

    private fun checkName(name: String): Boolean {
        val namePattern = Regex("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$")
        if (!namePattern.matches(name)) {
            return false
        }
        return true
    }
}