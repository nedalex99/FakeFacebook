package com.teme.fakefacebook.registration.repository

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.teme.fakefacebook.registration.models.User
import com.teme.fakefacebook.registration.signin.SignInFragment

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

    fun signIn(email: String, password: String): MutableLiveData<FirebaseUser> {
        val userSignedInMutableLiveData = MutableLiveData<FirebaseUser>()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    userSignedInMutableLiveData.value = FirebaseAuth.getInstance().currentUser
                } else {
                    userSignedInMutableLiveData.value = null
                }
            }
        return userSignedInMutableLiveData
    }

    fun hasEmailVerified(): Boolean {
        if (FirebaseAuth.getInstance().currentUser?.isEmailVerified!!) {
            return true
        }
        return false
    }
}