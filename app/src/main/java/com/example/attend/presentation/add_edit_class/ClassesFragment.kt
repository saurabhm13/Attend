package com.example.attend.presentation.add_edit_class

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attend.R
import com.example.attend.databinding.FragmentClassesBinding
import com.example.attend.model.local.AppDatabase
import com.example.attend.presentation.adapter.ClassAdapter
import com.example.attend.presentation.adapter.UserAdapter
import com.example.attend.utils.Constants.Companion.ADMIN
import com.example.attend.utils.Constants.Companion.CLASS_ID
import com.example.attend.utils.Constants.Companion.CLASS_NAME
import com.example.attend.utils.Constants.Companion.DATE
import com.example.attend.utils.Constants.Companion.FROM
import com.example.attend.utils.Constants.Companion.NAME
import com.example.attend.utils.Constants.Companion.TEACHER
import com.example.attend.utils.Constants.Companion.TO
import com.example.attend.utils.Constants.Companion.USER_TYPE

class ClassesFragment : Fragment() {

    private lateinit var binding: FragmentClassesBinding
    private lateinit var classViewModel: ClassViewModel

    private lateinit var userType: String
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClassesBinding.inflate(layoutInflater, container, false)

        userType = activity?.intent?.getStringExtra(USER_TYPE).toString()
        name = activity?.intent?.getStringExtra(NAME).toString()

        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val classDao = AppDatabase.getInstance(requireContext()).classDao()
        val enrollmentDao = AppDatabase.getInstance(requireContext()).enrollmentDao()
        val attendanceDao = AppDatabase.getInstance(requireContext()).attendanceDao()
        val attendanceReportDao = AppDatabase.getInstance(requireContext()).attendanceReportDao()

        classViewModel = ViewModelProvider(
            this,
            ClassViewModelFactory(classDao, userDao, enrollmentDao, attendanceDao, attendanceReportDao)
        )[ClassViewModel::class.java]

        classViewModel.errorCallback = {
            Toast.makeText(activity, "Error: $it", Toast.LENGTH_SHORT).show()
        }

        binding.addEditClass.setOnClickListener {
            val intoAddEditClass = Intent(activity, AddEditClassActivity::class.java)
            startActivity(intoAddEditClass)
        }

        prepareRecyclerView()

        return binding.root
    }

    private fun prepareRecyclerView() {
        val classAdapter = ClassAdapter(
            onItemClick = {
                val intoClassDetails = Intent(activity, ClassDetailsActivity::class.java)
                intoClassDetails.putExtra(CLASS_ID, it.class_id)
                intoClassDetails.putExtra(CLASS_NAME, it.class_name)
                intoClassDetails.putExtra(TEACHER, it.teacher)
                intoClassDetails.putExtra(DATE, it.date)
                intoClassDetails.putExtra(FROM, it.from)
                intoClassDetails.putExtra(TO, it.to)
                intoClassDetails.putExtra(USER_TYPE, userType)
                startActivity(intoClassDetails)
            },
            onEditClick = {
                val intoAddEditClass = Intent(activity, AddEditClassActivity::class.java)
                intoAddEditClass.putExtra(CLASS_ID, it.class_id)
                intoAddEditClass.putExtra(CLASS_NAME, it.class_name)
                intoAddEditClass.putExtra(TEACHER, it.teacher)
                intoAddEditClass.putExtra(DATE, it.date)
                intoAddEditClass.putExtra(FROM, it.from)
                intoAddEditClass.putExtra(TO, it.to)
                startActivity(intoAddEditClass)
            }
        )

        binding.rvClasses.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = classAdapter
        }

        if (userType == ADMIN) {
            classViewModel.getAllClass().observe(viewLifecycleOwner) {
                classAdapter.setClassList(it)
            }
        }else if (userType == TEACHER) {
            classViewModel.getClassByTeacher(name).observe(viewLifecycleOwner) {
                classAdapter.setClassList(it)
            }
        }

    }
}