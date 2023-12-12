package com.example.attend.presentation.add_edit_class

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.attend.model.local.dao.AttendanceDao
import com.example.attend.model.local.dao.AttendanceReportDao
import com.example.attend.model.local.dao.ClassDao
import com.example.attend.model.local.dao.EnrollmentDao
import com.example.attend.model.local.dao.UserDao

class ClassViewModelFactory(
    private val classDao: ClassDao,
    private val userDao: UserDao,
    private val enrollmentDao: EnrollmentDao,
    private val attendanceDao: AttendanceDao,
    private val attendanceReportDao: AttendanceReportDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ClassViewModel(classDao, userDao, enrollmentDao, attendanceDao, attendanceReportDao) as T
    }
}