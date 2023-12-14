package com.example.attend.presentation.student_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.attend.R
import com.example.attend.databinding.FragmentStudentAttendanceBinding
import com.example.attend.model.local.AppDatabase
import com.example.attend.presentation.add_edit_class.ClassViewModel
import com.example.attend.presentation.add_edit_class.ClassViewModelFactory
import com.example.attend.utils.Constants.Companion.ABSENT
import com.example.attend.utils.Constants.Companion.NAME
import com.example.attend.utils.Constants.Companion.PRESENT
import com.example.attend.utils.Constants.Companion.USER_ID

class StudentAttendanceFragment : Fragment() {

    private lateinit var binding: FragmentStudentAttendanceBinding
    private lateinit var classViewModel: ClassViewModel

    private var studentId: Long = 0
    private lateinit var studentName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentAttendanceBinding.inflate(layoutInflater, container, false)

        studentId = activity?.intent?.getLongExtra(USER_ID, 0)!!
        studentName = activity?.intent?.getStringExtra(NAME).toString()

        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val classDao = AppDatabase.getInstance(requireContext()).classDao()
        val enrollmentDao = AppDatabase.getInstance(requireContext()).enrollmentDao()
        val attendanceDao = AppDatabase.getInstance(requireContext()).attendanceDao()
        val attendanceReportDao = AppDatabase.getInstance(requireContext()).attendanceReportDao()

        classViewModel = ViewModelProvider(
            this,
            ClassViewModelFactory(classDao, userDao, enrollmentDao, attendanceDao, attendanceReportDao)
        )[ClassViewModel::class.java]

        classViewModel.getAttendanceForStudent(studentId).observe(viewLifecycleOwner) {attendanceList ->

            var present = 0
            var absence = 0
            var tardy = 0

            for (attendance in attendanceList) {
                when (attendance.attendance_status) {
                    PRESENT -> {
                        present++
                    }
                    ABSENT -> {
                        absence++
                    }
                    else -> {
                        tardy++
                    }
                }
            }

            binding.studentName.text = studentName
            binding.present.text = present.toString()
            binding.absence.text = absence.toString()
            binding.tardy.text = tardy.toString()
        }

        return binding.root
    }

}