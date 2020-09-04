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
import kotlinx.android.synthetic.main.fragment_email_address.*
import kotlinx.android.synthetic.main.fragment_email_address.back_img
import kotlinx.android.synthetic.main.fragment_email_address.next_btn
import kotlinx.android.synthetic.main.fragment_mobile_number.*

class EmailAddressFragment : Fragment() {

    private var uuid: String? = null
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference

        uuid = getUUID()

        setupUI()
    }


    private fun setupUI() {
        next_btn.setOnClickListener {
            addEmailAddressToUser(uuid)
            goToTermsFragment()
        }

        skip_tv.setOnClickListener {
            addEmailAddressToUser(uuid)
            goToTermsFragment()
        }

        back_img.setOnClickListener {
            createAlertDialog()
        }
    }

    private fun goToTermsFragment() {
        val action = uuid?.let { it1 ->
            EmailAddressFragmentDirections.actionEmailAddressFragmentToTermsPrivacyFragment(uuid = it1)
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
                if (uuid != null) {
                    deleteUser(uuid)
                }
                view?.findNavController()
                    ?.navigate(R.id.action_emailAddressFragment_to_signUpFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }

    private fun addEmailAddressToUser(uuid: String?) {
        uuid?.let {
            database.child("users").child(it).child("email")
                .setValue(email_et.text.toString())
        }
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