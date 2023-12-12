package com.example.attend.presentation.add_edit_class

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attend.R
import com.example.attend.databinding.ActivityAddStudentsBinding
import com.example.attend.model.data.User
import com.example.attend.model.local.AppDatabase
import com.example.attend.presentation.adapter.AddStudentsInClassAdapter
import com.example.attend.utils.Constants.Companion.CLASS_ID
import com.example.attend.utils.Constants.Companion.STUDENT

class AddStudentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentsBinding
    private lateinit var classViewModel: ClassViewModel

    private var classId: Long = 0
    private var enrolledStudents: List<User> = emptyList()
    private var allStudents: List<User> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDao = AppDatabase.getInstance(applicationContext).userDao()
        val classDao = AppDatabase.getInstance(applicationContext).classDao()
        val enrollmentDao = AppDatabase.getInstance(applicationContext).enrollmentDao()
        val attendanceDao = AppDatabase.getInstance(applicationContext).attendanceDao()
        val attendanceReportDao = AppDatabase.getInstance(applicationContext).attendanceReportDao()

        classViewModel =
            ViewModelProvider(
                this,
                ClassViewModelFactory(classDao, userDao, enrollmentDao, attendanceDao, attendanceReportDao)
            )[ClassViewModel::class.java]

        getExtrasData()

        binding.back.setOnClickListener {
            finish()
        }

        prepareRecyclerView()

    }

    private fun prepareRecyclerView() {
        val studentAdapter = AddStudentsInClassAdapter(
            onAddClick = {user, isCheck ->
                if (isCheck) {
                    classViewModel.enrollStudentInClass(user.user_id, classId)
                }else {
                    classViewModel.removeStudentFromClass(user.user_id, classId)
                }

            }
        )

        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(this@AddStudentsActivity, LinearLayoutManager.VERTICAL, false)
            adapter = studentAdapter
        }

        classViewModel.getUserByUserType(STUDENT).observe(this, Observer {
            allStudents = it
            studentAdapter.setAllStudentList(it)

        })

        // Example of getting enrolled students in a class
        classViewModel.getEnrolledStudentsInClass(classId).observe(this, Observer {
            enrolledStudents = it
            studentAdapter.setEnrolledStudentList(it)
        })
    }

    private fun getExtrasData() {
        classId = intent.getLongExtra(CLASS_ID, 0)
    }
}