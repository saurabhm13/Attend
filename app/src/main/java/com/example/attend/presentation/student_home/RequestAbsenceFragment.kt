package com.example.attend.presentation.student_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.attend.R
import com.example.attend.databinding.FragmentRequestAbsenceBinding
import com.example.attend.model.local.AppDatabase
import com.example.attend.presentation.add_user.UserViewModel
import com.example.attend.presentation.add_user.UserViewModelFactory
import com.example.attend.utils.Constants.Companion.USER_ID

class RequestAbsenceFragment : Fragment() {

    private lateinit var binding: FragmentRequestAbsenceBinding
    private lateinit var userViewModel: UserViewModel

    private var studentId: Long = 0
    private lateinit var title: String
    private lateinit var reason: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestAbsenceBinding.inflate(layoutInflater, container, false)

        studentId = activity?.intent?.getLongExtra(USER_ID, 0)!!

        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val absenceDao = AppDatabase.getInstance(requireContext()).excuseAbsenceDao()

        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userDao, absenceDao)
        )[UserViewModel::class.java]

        binding.send.setOnClickListener {
            title = binding.title.editText?.text.toString().trim()
            reason = binding.reason.editText?.text.toString().trim()

            if (title != "" && reason != "") {
                userViewModel.insertAbsence(title, reason, studentId)
            }
        }

        userViewModel.successCallBack = {
            Toast.makeText(activity, "Submitted", Toast.LENGTH_SHORT).show()
            binding.title.editText?.setText("")
            binding.reason.editText?.setText("")
        }

        userViewModel.errorCallBack = {
            Toast.makeText(activity, "Error: $it", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

}