package com.teme.fakefacebook.fragments.signup

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.teme.fakefacebook.R
import com.teme.fakefacebook.models.BirthDate
import com.teme.fakefacebook.models.User
import kotlinx.android.synthetic.main.fragment_date_of_birth.*
import kotlinx.android.synthetic.main.fragment_date_of_birth.back_img
import kotlinx.android.synthetic.main.fragment_date_of_birth.error
import kotlinx.android.synthetic.main.fragment_first_last_name.*
import java.time.LocalDateTime


class DateOfBirthFragment : Fragment() {

    private lateinit var firstName: String
    private lateinit var lastName: String
    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_of_birth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //userId = getUserId()

        getName()

        setupUI()
    }

    private fun setupUI() {
        choose_date_btn.setOnClickListener {
            if (!checkYearChosen()) {
                showError()
                /*Toast.makeText(
                    context,
                    "You are too young to have a Facebook account!",
                    Toast.LENGTH_LONG
                ).show()*/
            } else {
                hideError()
                //userId?.let { it1 -> addBirthDateToUser(it1) }
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
        val birthDate = BirthDate(date_picker.year, date_picker.month, date_picker.dayOfMonth)
        val action = DateOfBirthFragmentDirections
            .actionDateOfBirthFragmentToGenderFragment(
                firstName = firstName,
                lastName = lastName,
                day = birthDate.day.toString(),
                month = birthDate.month.toString(),
                year = birthDate.year.toString()
            )

        view?.findNavController()?.navigate(action)
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
                    ?.navigate(R.id.action_dateOfBirthFragment_to_signInFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }

    private fun addBirthDateToUser(userId: String) {
        val birthDate = BirthDate(date_picker.year, date_picker.month, date_picker.dayOfMonth)

        userId.let {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(it)
                .update("birthdate", birthDate)
        }

        //database.child("users").child(uuid).child("birthDate").setValue(birthDate)
    }

    private fun deleteUser(userId: String?) {
        userId?.let {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(it)
                .delete()
        }
    }

    private fun showError() {
        error.error = true.toString()
        error.visibility = View.VISIBLE
    }

    private fun hideError() {
        error.error = null
        error.visibility = View.GONE
    }

    private fun getName() {
        arguments?.let {
            val args = DateOfBirthFragmentArgs.fromBundle(requireArguments())
            val firstName = args.firstName
            val lastName = args.lastName
            this.firstName = firstName
            this.lastName = lastName
        }
    }

    private fun getUserId(): String? {
        arguments?.let {
            val args = DateOfBirthFragmentArgs.fromBundle(requireArguments())
            //return args.userId
        }
        return null
    }


}