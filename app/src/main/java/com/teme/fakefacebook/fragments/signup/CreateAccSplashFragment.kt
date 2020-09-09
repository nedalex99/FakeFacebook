package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.teme.fakefacebook.R
import com.teme.fakefacebook.models.User
import kotlinx.android.synthetic.main.fragment_create_acc_splash.*
import java.util.concurrent.TimeUnit

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
                    createPhoneAuth()
                } else {
                    Toast.makeText(context, createUserTask.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun createPhoneAuth() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            user?.mobileNumber.toString(),
            60,
            TimeUnit.SECONDS,
            requireActivity(),
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Log.i("Verification completed", p0.smsCode.toString())
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Log.i("Verification failed", p0.message.toString())
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    goToAccountConfirmationFragment(p0)
                }

            }
        )
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
                            //goToAccountConfirmationFragment()
                        } else {
                            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }

    private fun goToAccountConfirmationFragment(code: String) {
        val action = user?.let {
            CreateAccSplashFragmentDirections
                .actionCreateAccSplashFragmentToAccountConfirmationFragment(
                    user = it,
                    code = code
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