package com.teme.fakefacebook.fragments.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.teme.fakefacebook.R
import kotlinx.android.synthetic.main.fragment_date_of_birth.*


class DateOfBirthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_of_birth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        choose_date_btn.setOnClickListener {
            date_tv.text = date_picker.dayOfMonth.toString() + "/" + date_picker.month.toString() + "/" + date_picker.year.toString()
            view.findNavController().navigate(R.id.action_dateOfBirthFragment_to_genderFragment)
        }

        back_img.setOnClickListener {
            view.findNavController().navigate(R.id.action_dateOfBirthFragment_to_signUpFragment)
        }
    }
}