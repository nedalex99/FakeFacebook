package com.teme.fakefacebook.registration.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teme.fakefacebook.R
import com.teme.fakefacebook.registration.models.User
import com.teme.fakefacebook.registration.viewmodels.AuthViewModel

class CreateAccSplashFragment : Fragment() {

    private var user: User? = null

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_acc_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        user = getUser()

        createUserWithEmailAndPassword()
    }

    private fun createUserWithEmailAndPassword() {
        val email = user?.email.toString()
        val password = user?.password.toString()
        viewModel.createUserWithEmailAndPassword(email, password)
        viewModel.createdUserLiveData.observe(viewLifecycleOwner,
            { firebaseUser ->
                if (firebaseUser != null) {
                    sendEmailConfirmation()
                    Toast.makeText(context, "User created!", Toast.LENGTH_LONG).show()
                    addUserToFirestore(viewModel.createdUserLiveData.value?.uid)
                }
            })
    }

    private fun addUserToFirestore(userId: String?) {
        userId?.let {
            user?.let { it1 ->
                viewModel.createUserInFirestore(it1, it)
                goToSignInFragment()
            }
        }
    }

    private fun sendEmailConfirmation() {
        viewModel.sendEmailConfirmation()
    }

    private fun goToSignInFragment() {
        view?.findNavController()?.navigate(R.id.action_createAccSplashFragment_to_signInFragment)
    }

    private fun getUser(): User? {
        arguments?.let {
            val args = CreateAccSplashFragmentArgs.fromBundle(requireArguments())
            return args.user
        }
        return null
    }

}