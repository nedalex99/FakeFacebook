package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
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
import com.teme.fakefacebook.models.BirthDate
import com.teme.fakefacebook.models.Name
import kotlinx.android.synthetic.main.fragment_date_of_birth.*
import kotlinx.android.synthetic.main.fragment_date_of_birth.back_img
import kotlinx.android.synthetic.main.fragment_first_last_name.*


class DateOfBirthFragment : Fragment() {

    private var uuid: String? = null
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_of_birth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference

        uuid = getUUID()

        setupUI()
    }

    private fun setupUI() {
        choose_date_btn.setOnClickListener {
            uuid?.let { it1 -> addBirthDateToUser(it1) }
            goToGenderFragment()
        }

        back_img.setOnClickListener {
            createAlertDialog()
        }
    }

    private fun goToGenderFragment() {
        val action = uuid?.let { it1 ->
            DateOfBirthFragmentDirections.actionDateOfBirthFragmentToGenderFragment(uuid = it1)
        }
        if (action != null) {
            view?.findNavController()?.navigate(action)
        }
    }

    private fun createAlertDialog() {
        activity?.let { it1 -> AlertDialog.Builder(it1) }?.apply {
            setTitle("Do you want to stop creating your account?")
            setMessage("If you stop nou, you'll lose any\n progress you've made.")
            setPositiveButton(
                "Stop Creating Account"
            ) { _, _ ->
                if (uuid != null) {
                    deleteUser(uuid)
                }
                view?.findNavController()
                    ?.navigate(R.id.action_dateOfBirthFragment_to_signUpFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }

    private fun addBirthDateToUser(uuid: String) {
        val birthDate = BirthDate(date_picker.year, date_picker.month, date_picker.dayOfMonth)
        database.child("users").child(uuid).child("birthDate").setValue(birthDate)
    }

    private fun deleteUser(uuid: String?) {
        database.child("users").child(uuid.toString()).setValue(null)
    }

    private fun getUUID(): String? {
        arguments?.let {
            val args = DateOfBirthFragmentArgs.fromBundle(requireArguments())
            return args.uuid
        }
        return null
    }


}