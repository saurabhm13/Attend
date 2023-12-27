package com.example.attend.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.attend.databinding.AttendancePopupBinding
import com.example.attend.utils.Constants.Companion.ABSENT
import com.example.attend.utils.Constants.Companion.PRESENT
import com.example.attend.utils.Constants.Companion.TARDY

class AttendanceDialog(private val name: String) : DialogFragment() {

    private lateinit var binding: AttendancePopupBinding

    var clickCallback: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AttendancePopupBinding.inflate(layoutInflater, container, false)

        binding.studentName.text = name

        binding.present.setOnClickListener {
            clickCallback?.invoke(PRESENT)
            dismiss()
        }

        binding.absent.setOnClickListener {
            clickCallback?.invoke(ABSENT)
            dismiss()
        }

        binding.tardy.setOnClickListener {
            clickCallback?.invoke(TARDY)
            dismiss()
        }

        return binding.root
    }
}
