package com.example.attend.presentation.add_edit_class

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attend.model.data.Attendance
import com.example.attend.model.data.AttendanceReport
import com.example.attend.model.data.ClassEntity
import com.example.attend.model.data.Enrollment
import com.example.attend.model.data.StudentWithEnrollmentStatus
import com.example.attend.model.data.User
import com.example.attend.model.local.dao.AttendanceDao
import com.example.attend.model.local.dao.AttendanceReportDao
import com.example.attend.model.local.dao.ClassDao
import com.example.attend.model.local.dao.EnrollmentDao
import com.example.attend.model.local.dao.UserDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class ClassViewModel(
    private val classDao: ClassDao,
    private val userDao: UserDao,
    private val enrollmentDao: EnrollmentDao,
    private val attendanceDao: AttendanceDao,
    private val attendanceReportDao: AttendanceReportDao
): ViewModel() {

    private var _classEditStatus = MutableLiveData<Boolean>()
    val classEditStatus: LiveData<Boolean> get() = _classEditStatus

    private val _studentsWithEnrollment = MutableLiveData<List<StudentWithEnrollmentStatus>>()
    val studentsWithEnrollment: LiveData<List<StudentWithEnrollmentStatus>> get() = _studentsWithEnrollment

    fun addEditClass(classEntity: ClassEntity){
        try {
            viewModelScope.launch {
                val classId = classDao.upsertClass(classEntity)
                if (classEntity.class_id.toInt() == 0) {
                    val attendanceReport = AttendanceReport(0, classId, classEntity.class_name, classEntity.teacher, 0, 0, 0)
                    createAttendanceReport(attendanceReport)
                }else {

                }
            }
            _classEditStatus.value = true
        }catch (e: Exception) {
            _classEditStatus.value = false
        }
    }

    fun getClassById(classId: Long) = classDao.getClassById(classId)

    fun getAllClass() = classDao.getAllClasses()

    fun getClassByTeacher(teacher: String) = classDao.getClassByTeacher(teacher)

    fun deleteClass(classEntity: ClassEntity) {
        try {
            viewModelScope.launch {
                classDao.deleteClass(classEntity)
                _classEditStatus.value = true
            }
        }catch (e: Exception) {
            _classEditStatus.value = false
        }
    }

    fun enrollStudentInClass(studentId: Long, classId: Long) {
        viewModelScope.launch {
            val enrollment = Enrollment(student_id = studentId, class_id = classId)
            enrollmentDao.upsertEnrollment(enrollment)
        }
    }

    fun removeStudentFromClass(studentId: Long, classId: Long) {
        viewModelScope.launch {
            enrollmentDao.removeStudentFromClass(studentId, classId)
        }
    }

    // Get all students enrolled in a class
    fun getEnrolledStudentsInClass(classId: Long): LiveData<List<User>> {
        return userDao.getStudentsInClass(classId)
    }

    fun getUserByUserType(userType: String) = userDao.getUserByType(userType)

    fun getClassAttendance(classId: Long) = attendanceDao.getClassAttendance(classId)

    fun addStudentAttendance(studentId: Long, classId: Long, attendanceStatus: String) {
        try {
            viewModelScope.launch {
                val attendance = Attendance(0, classId, studentId, attendanceStatus, getCurrentDate())
                attendanceDao.insertAttendance(attendance)
            }
        }catch (e: Exception) {

        }
    }

    fun removeStudentAttendance(studentId: Long, classId: Long) {
        try {
            viewModelScope.launch {
                attendanceDao.removeAttendanceOfStudentFromClass(studentId, classId)
            }
        }catch (e: Exception) {

        }
    }

    private fun createAttendanceReport(attendanceReport: AttendanceReport) {
        try {
            viewModelScope.launch {
                attendanceReportDao.insertAttendanceReport(attendanceReport)
            }
        }catch (e: Exception) {

        }
    }

    fun updateAttendanceReport(attendanceReport: AttendanceReport) {
        try {
            viewModelScope.launch {
                attendanceReportDao.upsertAttendanceReport(attendanceReport)
            }
        }catch (e: Exception) {

        }
    }

    fun getAllAttendanceReport() = attendanceReportDao.getAllAttendanceReport()

    fun getAttendanceReportForClass(classId: Long) = attendanceReportDao.getAttendanceReportForClass(classId)

    fun getAttendanceReportForTeacher(teacher: String) = attendanceReportDao.getAttendanceReportForTeacher(teacher)

    fun getAttendanceForStudent(studentId: Long) = attendanceDao.getAttendanceForStudent(studentId)

    fun deleteAttendanceReport(classId: Long) {
        try {
            viewModelScope.launch{
                attendanceReportDao.deleteAttendanceReport(classId)
            }
        }catch (e: Exception) {

        }
    }

    private fun getCurrentDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        return formatter.format(time)
    }

//    fun getAllStudentsAndCheckEnrollment(classId: Long, userType: String) {
//        viewModelScope.launch {
//            val allStudents = userDao.getUserByType(userType)
//            val enrolledStudents = enrollmentDao.getEnrolledStudentsInClass(classId)
//
//            val studentsWithEnrollmentStatus = allStudents.map { student ->
//                StudentWithEnrollmentStatus(
//                    student = student,
//                    isEnrolled = enrolledStudents.any { it.user_id == student.user_id }
//                )
//            }
//
//            _studentsWithEnrollmentStatus.value = studentsWithEnrollmentStatus
//        }
//    }


}