package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.teme.fakefacebook.R
import kotlinx.android.synthetic.main.fragment_password.*
import kotlinx.android.synthetic.main.fragment_password.back_img
import kotlinx.android.synthetic.main.fragment_password.next_btn


class PasswordFragment : Fragment() {

    private var userId: String? = null
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference

        userId = getUserId()

        setupUI()
    }

    private fun setupUI() {
        next_btn.setOnClickListener {
            if (password_et.text.isEmpty()) {
                showError()
            } else {
                hideError()
                userId?.let { it1 -> addPasswordToUser(it1) }
                goToEmailFragment()
            }
        }

        back_img.setOnClickListener {
            createAlertDialog()
        }
    }

    private fun goToEmailFragment() {
        val action = userId?.let { it1 ->
            PasswordFragmentDirections.actionPasswordFragmentToEmailAddressFragment(userId = it1)
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
                if (userId != null) {
                    deleteUser(userId)
                }
                view?.findNavController()
                    ?.navigate(R.id.action_passwordFragment_to_signInFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }

    private fun addPasswordToUser(userId: String?) {
        userId?.let {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(it)
                .update("password", password_et.text.toString())
        }
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

    private fun getUserId(): String? {
        arguments?.let {
            val args = GenderFragmentArgs.fromBundle(requireArguments())
            return args.userId
        }
        return null
    }

}