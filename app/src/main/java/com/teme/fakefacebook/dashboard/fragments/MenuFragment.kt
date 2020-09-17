package com.teme.fakefacebook.dashboard.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.teme.fakefacebook.R
import com.teme.fakefacebook.registration.models.User
import com.teme.fakefacebook.registration.signup.MainActivity
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

        FirebaseFirestore.getInstance().collection("users")
            .document(uid).get().addOnSuccessListener {
                val user = it.toObject<User>()
                name_tv.text = user?.firstName.toString() + " " + user?.lastName.toString()
            }

        log_out_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }
}