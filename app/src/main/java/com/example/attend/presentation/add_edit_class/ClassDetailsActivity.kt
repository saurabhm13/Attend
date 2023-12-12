package com.example.attend.presentation.add_edit_class

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attend.R
import com.example.attend.databinding.ActivityClassDetailsBinding
import com.example.attend.model.data.AttendanceReport
import com.example.attend.model.local.AppDatabase
import com.example.attend.presentation.adapter.StudentAttendanceAdapter
import com.example.attend.presentation.adapter.UserAdapter
import com.example.attend.utils.Constants
import com.example.attend.utils.Constants.Companion.ABSENT
import com.example.attend.utils.Constants.Companion.CLASS_ID
import com.example.attend.utils.Constants.Companion.CLASS_NAME
import com.example.attend.utils.Constants.Companion.PRESENT
import com.example.attend.utils.Constants.Companion.TARDY
import kotlin.math.abs

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
        teacher = intent.getStringExtra(Constants.TEACHER).toString()
        date = intent.getStringExtra(Constants.DATE).toString()
        from = intent.getStringExtra(Constants.FROM).toString()
        to = intent.getStringExtra(Constants.TO).toString()

        binding.className.text = className
        binding.teacherName.text = teacher
        binding.date.text = date
        binding.time.text = "$from - $to"
    }

    private fun prepareRecyclerView() {
        val studentAdapter = StudentAttendanceAdapter(
            onPresentClick = {
                present += 1
                val attendanceReport = AttendanceReport(attendanceReportId, classId, className, teacher, present, absence, tardy)
                classViewModel.updateAttendanceReport(attendanceReport)
                classViewModel.addStudentAttendance(it.user_id, classId, PRESENT)
            },
            onAbsentClick = {
                absence += 1
                val attendanceReport = AttendanceReport(attendanceReportId, classId, className, teacher, present, absence, tardy)
                classViewModel.updateAttendanceReport(attendanceReport)
                classViewModel.addStudentAttendance(it.user_id, classId, ABSENT)
            },
            onTardyClick = {
                tardy += 1
                val attendanceReport = AttendanceReport(attendanceReportId, classId, className, teacher, present, absence, tardy)
                classViewModel.updateAttendanceReport(attendanceReport)
                classViewModel.addStudentAttendance(it.user_id, classId, TARDY)
            },
            onAttendanceStatusClick = {user, status ->
                when(status) {
                    PRESENT -> {
                        present -= 1
                        val attendanceReport = AttendanceReport(attendanceReportId, classId, className, teacher, present, absence, tardy)
                        classViewModel.updateAttendanceReport(attendanceReport)
                    }
                    ABSENT -> {
                        absence -= 1
                        val attendanceReport = AttendanceReport(attendanceReportId, classId, className, teacher, present, absence, tardy)
                        classViewModel.updateAttendanceReport(attendanceReport)
                    }
                    else -> {
                        tardy -= 1
                        val attendanceReport = AttendanceReport(attendanceReportId, classId, className, teacher, present, absence, tardy)
                        classViewModel.updateAttendanceReport(attendanceReport)
                    }
                }
                classViewModel.removeStudentAttendance(user.user_id, classId)
            }
        )

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
}