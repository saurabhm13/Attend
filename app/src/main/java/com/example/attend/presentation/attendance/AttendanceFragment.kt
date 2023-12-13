package com.example.attend.presentation.attendance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attend.R
import com.example.attend.databinding.FragmentAttendanceBinding
import com.example.attend.model.local.AppDatabase
import com.example.attend.presentation.adapter.AttendanceReportAdapter
import com.example.attend.presentation.add_edit_class.ClassViewModel
import com.example.attend.presentation.add_edit_class.ClassViewModelFactory
import com.example.attend.utils.Constants
import com.example.attend.utils.Constants.Companion.ADMIN
import com.example.attend.utils.Constants.Companion.TEACHER

class AttendanceFragment : Fragment() {

    private lateinit var binding: FragmentAttendanceBinding
    private lateinit var classViewModel: ClassViewModel

    private lateinit var userType: String
    private lateinit var name: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAttendanceBinding.inflate(layoutInflater, container, false)

        userType = activity?.intent?.getStringExtra(Constants.USER_TYPE).toString()
        name = activity?.intent?.getStringExtra(Constants.NAME).toString()

        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val classDao = AppDatabase.getInstance(requireContext()).classDao()
        val enrollmentDao = AppDatabase.getInstance(requireContext()).enrollmentDao()
        val attendanceDao = AppDatabase.getInstance(requireContext()).attendanceDao()
        val attendanceReportDao = AppDatabase.getInstance(requireContext()).attendanceReportDao()

        classViewModel = ViewModelProvider(
            this,
            ClassViewModelFactory(classDao, userDao, enrollmentDao, attendanceDao, attendanceReportDao)
        )[ClassViewModel::class.java]

        prepareRecyclerView()


        return binding.root
    }

    private fun prepareRecyclerView() {
        val attendanceReportAdapter = AttendanceReportAdapter()

        binding.rvAttendanceReport.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = attendanceReportAdapter
        }

        if (userType == ADMIN) {
            classViewModel.getAllAttendanceReport().observe(viewLifecycleOwner) {
                attendanceReportAdapter.setAttendanceList(it)
            }
        }else if (userType == TEACHER) {
            classViewModel.getAttendanceReportForTeacher(name).observe(viewLifecycleOwner) {
                attendanceReportAdapter.setAttendanceList(it)
            }
        }
    }
}