package com.teme.fakefacebook.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import com.teme.fakefacebook.R
import kotlinx.android.synthetic.main.fragment_quit_registration_dialog.*

class QuitRegistrationDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quit_registration_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        continue_acc_tv.setOnClickListener {
            dialog?.dismiss()
        }

        stop_acc_tv.setOnClickListener {
            parentFragment?.view?.findNavController()
                ?.navigate(R.id.action_quitRegistrationDialogFragment_to_signInFragment)
        }
    }

}