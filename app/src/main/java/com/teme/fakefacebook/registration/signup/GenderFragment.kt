package com.teme.fakefacebook.registration.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.teme.fakefacebook.R
import com.teme.fakefacebook.registration.models.User
import kotlinx.android.synthetic.main.fragment_gender.*
import kotlinx.android.synthetic.main.fragment_gender.back_img
import kotlinx.android.synthetic.main.fragment_gender.error
import kotlinx.android.synthetic.main.fragment_gender.next_btn
import java.util.*

class GenderFragment : Fragment() {

    private var user: User? = null

    private var gender: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gender, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getUser()

        setupUI()
    }

    private fun setupUI() {
        next_btn.setOnClickListener {
            if (radio_group.checkedRadioButtonId == -1) {
                showError()
            } else {
                user?.gender = gender
                goToMobileNumberFragment()
            }
        }


        radio_group.setOnCheckedChangeListener { _, checkedId ->
            hideError()
            gender = if (checkedId == R.id.male) {
                male.text.toString().decapitalize(Locale.ROOT)
            } else {
                female.text.toString().decapitalize(Locale.ROOT)
            }
        }

        back_img.setOnClickListener {
            createAlertDialog()
        }
    }

    private fun goToMobileNumberFragment() {
        val action = user?.let { it1 ->
            GenderFragmentDirections.actionGenderFragmentToMobileNumberFragment(
                user = it1
            )
        }
        action?.let {
            view?.findNavController()?.navigate(it)
        }
    }

    private fun createAlertDialog() {
        activity?.let { it1 -> AlertDialog.Builder(it1) }?.apply {
            setTitle("Do you want to stop creating your account?")
            setMessage("If you stop nou, you'll lose any\n progress you've made.")
            setPositiveButton(
                "Stop Creating Account"
            ) { _, _ ->
                view?.findNavController()
                    ?.navigate(R.id.action_genderFragment_to_signInFragment)
            }
            setNegativeButton("Continue Creating Account") { _, _ ->
                setCancelable(true)
            }
        }?.create()?.show()
    }

    private fun showError() {
        female.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        male.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        error.error = true.toString()
        change_tv.visibility = View.GONE
        error.visibility = View.VISIBLE
    }

    private fun hideError() {
        female.setTextColor(ContextCompat.getColor(requireContext(), R.color.textBlackColor))
        male.setTextColor(ContextCompat.getColor(requireContext(), R.color.textBlackColor))
        error.error = null
        change_tv.visibility = View.VISIBLE
        error.visibility = View.GONE
    }

    private fun getUser(): User? {
        arguments?.let {
            val args = GenderFragmentArgs.fromBundle(requireArguments())
            return args.user
        }
        return null
    }
}