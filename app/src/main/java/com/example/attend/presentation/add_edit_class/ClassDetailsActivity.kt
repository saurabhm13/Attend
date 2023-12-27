package com.example.attend.presentation.add_edit_class

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attend.databinding.ActivityClassDetailsBinding
import com.example.attend.model.data.AttendanceReport
import com.example.attend.model.local.AppDatabase
import com.example.attend.presentation.adapter.StudentAttendanceAdapter
import com.example.attend.utils.AttendanceDialog
import com.example.attend.utils.Constants
import com.example.attend.utils.Constants.Companion.ABSENT
import com.example.attend.utils.Constants.Companion.CLASS_ID
import com.example.attend.utils.Constants.Companion.CLASS_NAME
import com.example.attend.utils.Constants.Companion.PRESENT
import com.example.attend.utils.Constants.Companion.TARDY
import com.example.attend.utils.Constants.Companion.TEACHER
import com.example.attend.utils.Constants.Companion.USER_TYPE

class ClassDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassDetailsBinding
    private lateinit var classViewModel: ClassViewModel

    private var classId: Long = 0
    private lateinit var className: String
    private lateinit var teacher: String
    private lateinit var date: String
    private lateinit var from: String
    private lateinit var to: String

    private var attendanceReportId: Long = 0
    private var present: Int = 0
    private var absence: Int = 0
    private var tardy: Int = 0

    private lateinit var userType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassDetailsBinding.inflate(layoutInflater)
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

        userType = intent.getStringExtra(USER_TYPE).toString()

        classViewModel.errorCallback = {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
        }

        setClassDetails()
        prepareRecyclerView()

        classViewModel.getAttendanceReportForClass(classId).observe(this) {
            attendanceReportId = it.attendance_report_id
            present = it.present
            absence = it.absence
            tardy = it.tardy
        }

        binding.back.setOnClickListener {
            finish()
        }

        binding.addStudents.setOnClickListener {
            val intoAddStudent = Intent(this, AddStudentsActivity::class.java)
            intoAddStudent.putExtra(CLASS_ID, classId)
            startActivity(intoAddStudent)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setClassDetails() {
        classId = intent.getLongExtra(CLASS_ID, 0)
        className = intent.getStringExtra(CLASS_NAME).toString()
        teacher = intent.getStringExtra(TEACHER).toString()
        date = intent.getStringExtra(Constants.DATE).toString()
        from = intent.getStringExtra(Constants.FROM).toString()
        to = intent.getStringExtra(Constants.TO).toString()

        binding.className.text = className
        binding.teacherName.text = teacher
        binding.date.text = date
        binding.time.text = "$from - $to"
    }

    private fun prepareRecyclerView() {

        val studentAdapter: StudentAttendanceAdapter
        var attendanceDialog: AttendanceDialog

        if (userType == TEACHER) {
            studentAdapter = StudentAttendanceAdapter(
                onItemClick = {
                    attendanceDialog = AttendanceDialog(it.name)
                    attendanceDialog.show(supportFragmentManager, "AttendanceDialog")

                    attendanceDialog.clickCallback = {attendance ->
                        when (attendance) {
                            PRESENT -> {
                                present++
                                updateAttendanceReport()
                                classViewModel.addStudentAttendance(it.user_id, classId, PRESENT)
                            }
                            ABSENT -> {
                                absence++
                                updateAttendanceReport()
                                classViewModel.addStudentAttendance(it.user_id, classId, ABSENT)
                            }
                            TARDY -> {
                                tardy++
                                updateAttendanceReport()
                                classViewModel.addStudentAttendance(it.user_id, classId, TARDY)
                            }
                        }
                    }
                },
                onAttendanceStatusClick = {user, status ->
                    attendanceDialog = AttendanceDialog(user.name)
                    attendanceDialog.show(supportFragmentManager, "AttendanceDialog")
                    attendanceDialog.clickCallback = {attendance ->

                        when(status) {
                            PRESENT -> {
                                present -= 1
                                updateAttendanceReport()
                            }
                            ABSENT -> {
                                absence -= 1
                                updateAttendanceReport()
                            }
                            else -> {
                                tardy -= 1
                                updateAttendanceReport()
                            }
                        }
                        classViewModel.removeStudentAttendance(user.user_id, classId)

                        when (attendance) {
                            PRESENT -> {
                                present++
                                updateAttendanceReport()
                                classViewModel.addStudentAttendance(user.user_id, classId, PRESENT)
                            }
                            ABSENT -> {
                                absence++
                                updateAttendanceReport()
                                classViewModel.addStudentAttendance(user.user_id, classId, ABSENT)
                            }
                            TARDY -> {
                                tardy++
                                updateAttendanceReport()
                                classViewModel.addStudentAttendance(user.user_id, classId, TARDY)
                            }
                        }
                    }

                }
            )
        }else {
            studentAdapter = StudentAttendanceAdapter(
                onItemClick = {

                },
                onAttendanceStatusClick = {user, status ->

                }
            )
        }


        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(this@ClassDetailsActivity, LinearLayoutManager.VERTICAL, false)
            adapter = studentAdapter
        }

        classViewModel.getEnrolledStudentsInClass(classId).observe(this) {
            studentAdapter.setStudentList(it)
        }

        classViewModel.getClassAttendance(classId).observe(this) {
            studentAdapter.setAttendanceList(it)
        }
    }

    private fun updateAttendanceReport() {
        val attendanceReport = AttendanceReport(attendanceReportId, classId, className, teacher, present, absence, tardy)
        classViewModel.updateAttendanceReport(attendanceReport)
    }
}