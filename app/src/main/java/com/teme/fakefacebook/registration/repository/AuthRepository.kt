package com.teme.fakefacebook.registration.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.teme.fakefacebook.registration.models.User

class AuthRepository {

    fun firebaseCreateUserWithEmailAndPassword(
        email: String,
        password: String
    ): MutableLiveData<FirebaseUser> {
        val userCreatedMutableLiveData = MutableLiveData<FirebaseUser>()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (FirebaseAuth.getInstance().currentUser != null) {
                        userCreatedMutableLiveData.value = FirebaseAuth.getInstance().currentUser
                    }
                }
            }
        return userCreatedMutableLiveData
    }

    fun createUserInFirestore(
        authenticatedUser: User,
        createdUserId: String
    ): MutableLiveData<User> {
        val userCreatedInFirestoreMutableLiveData = MutableLiveData<User>()
        FirebaseFirestore.getInstance().collection("users").document(createdUserId)
            .set(authenticatedUser).addOnCompleteListener {
                if (it.isSuccessful) {
                    userCreatedInFirestoreMutableLiveData.value = authenticatedUser
                }
            }
        return userCreatedInFirestoreMutableLiveData
    }

    fun sendEmailConfirmation() {
        FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
    }

    fun signInUser() {

    }

}