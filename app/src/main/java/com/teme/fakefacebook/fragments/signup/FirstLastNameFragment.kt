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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.teme.fakefacebook.R
import com.teme.fakefacebook.models.Name
import kotlinx.android.synthetic.main.fragment_first_last_name.*
import java.util.*

class FirstLastNameFragment : Fragment() {

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_last_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference

        nextClick()

        firstNameTextChanged()
        lastNameTextChanged()

        back_img.setOnClickListener {
            activity?.let { it1 -> AlertDialog.Builder(it1) }?.apply {
                setTitle("Do you want to stop creating your account?")
                setMessage("If you stop nou, you'll lose any\n progress you've made.")
                setPositiveButton("Stop Creating Account"
                ) { _, _ ->
                    view.findNavController()
                        .navigate(R.id.action_firstLastNameFragment_to_signUpFragment)
                }
                setNegativeButton("Continue Creating Account"){_, _ ->
                    setCancelable(true)
                }
            }?.create()?.show()
        }
    }

    private fun writeNewUser(userId: String) {
        val user =
            Name(firstName = first_name_et.text.toString(), lastName = last_name_et.text.toString())
        val aid = database.push().key.toString()
        database.child("users").child("name").child(UUID.randomUUID().toString()).setValue(user)
    }

    private fun firstNameTextChanged() {
        var textOk = true
        first_name_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (s.isNotEmpty()) {
                        if (!checkName(s.toString())) {
                            showError("Your name is not correct")
                            //textOk = true
                        } else {
                            hideError()
                            //textOk = false
                        }
                    } else {
                        hideError()
                        //textOk = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun lastNameTextChanged() {
        var textOk = true
        last_name_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (s.isNotEmpty()) {
                        if (!checkName(s.toString())) {
                            showError("Your name is not correct")
                            //textOk = true
                        } else {
                            hideError()
                            //textOk = false
                        }
                    } else {
                        hideError()
                        //textOk = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun nextClick() {
        next_btn.setOnClickListener {
            when {
                first_name_et.text!!.isEmpty() && last_name_et.text!!.isEmpty() -> {
                    showError("Your name is not correct")
                }
                checkName(last_name_et.text.toString()) && checkName(first_name_et.text.toString()) -> {
                    writeNewUser("4")
                    view?.findNavController()
                        ?.navigate(R.id.action_firstLastNameFragment_to_dateOfBirthFragment)
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