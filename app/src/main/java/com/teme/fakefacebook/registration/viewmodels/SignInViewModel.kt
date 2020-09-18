package com.teme.fakefacebook.registration.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.teme.fakefacebook.registration.repository.AuthRepository

class SignInViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    lateinit var signedIndUserLiveData: LiveData<FirebaseUser>

    fun signInUser(email: String, password: String) {
        signedIndUserLiveData = authRepository.signIn(email, password)
    }

    fun hasEmailVerified(): Boolean {
        return authRepository.hasEmailVerified()
    }

}