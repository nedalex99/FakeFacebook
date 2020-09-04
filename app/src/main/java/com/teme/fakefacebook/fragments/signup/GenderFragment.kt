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
import kotlinx.android.synthetic.main.fragment_gender.*
import kotlinx.android.synthetic.main.fragment_gender.back_img

class GenderFragment : Fragment() {

    private var uuid: String? = null
    private lateinit var gender: String
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gender, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uuid = getUUID()

        gender = ""
        database = Firebase.database.reference

        setupUI()
    }

    private fun setupUI() {
        next_btn.setOnClickListener {
            uuid?.let { addGenderToUser(it) }
            goToMobileNumberFragment()
        }

        radio_group.setOnCheckedChangeListener { group, checkedId ->
            gender = if (checkedId == R.id.male) {
                male.text.toString()
            } else {
                female.text.toString()
            }
        }

        back_img.setOnClickListener {
            createAlertDialog()
        }
    }

    private fun goToMobileNumberFragment() {
        val action = uuid?.let { it1 ->
            GenderFragmentDirections.actionGenderFragmentToMobileNumberFragment(uuid = it1)
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
                    ?.navigate(R.id.action_genderFragment_to_signUpFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }

    private fun addGenderToUser(uuid: String) {
        database.child("users").child(uuid).child("gender").setValue(gender)
    }

    private fun deleteUser(uuid: String?) {
        database.child("users").child(uuid.toString()).setValue(null)
    }

    private fun getUUID(): String? {
        arguments?.let {
            val args = GenderFragmentArgs.fromBundle(requireArguments())
            return args.uuid
        }
        return null
    }
}