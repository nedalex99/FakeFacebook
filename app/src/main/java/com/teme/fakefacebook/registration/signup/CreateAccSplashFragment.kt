package com.teme.fakefacebook.registration.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.teme.fakefacebook.R
import com.teme.fakefacebook.registration.models.User
import kotlinx.android.synthetic.main.fragment_create_acc_splash.*

class CreateAccSplashFragment : Fragment() {

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_acc_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getUser()

        createUserWithEmailAndPassword()
    }

    private fun createUserWithEmailAndPassword() {
        val email = user?.email.toString()
        val password = user?.password.toString()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { createUserTask ->
                if (createUserTask.isSuccessful) {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    addUserToFirestore(userId)
                    sendEmailConfirmation()
                    //createPhoneAuth()
                } else {
                    Toast.makeText(context, createUserTask.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addUserToFirestore(userId: String?) {
        userId?.let {
            user?.let { it1 ->
                FirebaseFirestore.getInstance().collection("users")
                    .document(it)
                    .set(it1)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            progress_bar.visibility = View.GONE
                            goToSignInFragment()
                            //goToAccountConfirmationFragment()
                        } else {
                            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }

    private fun sendEmailConfirmation() {
        FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
    }

    private fun goToSignInFragment() {
        view?.findNavController()?.navigate(R.id.action_createAccSplashFragment_to_signInFragment)
    }

    private fun goToAccountConfirmationFragment() {
        val action = user?.let {
            CreateAccSplashFragmentDirections.actionCreateAccSplashFragmentToAccountConfirmationFragment(
                user = it
            )
        }
        action?.let {
            view?.findNavController()
                ?.navigate(it)
        }
    }

    private fun getUser(): User? {
        arguments?.let {
            val args = CreateAccSplashFragmentArgs.fromBundle(requireArguments())
            return args.user
        }
        return null
    }

}