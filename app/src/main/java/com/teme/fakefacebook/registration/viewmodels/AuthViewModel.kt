package com.teme.fakefacebook.registration.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.teme.fakefacebook.registration.models.User
import com.teme.fakefacebook.registration.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    lateinit var createdUserLiveData: LiveData<FirebaseUser>
    private lateinit var createdUserInFirestoreLiveData: LiveData<User>

    fun createUserWithEmailAndPassword(email: String, password: String) {
        createdUserLiveData = authRepository.firebaseCreateUserWithEmailAndPassword(email, password)
    }

    fun createUserInFirestore(user: User, createdUserId: String) {
        createdUserInFirestoreLiveData = authRepository.createUserInFirestore(user, createdUserId)
    }

    fun sendEmailConfirmation() {
        authRepository.sendEmailConfirmation()
    }

}