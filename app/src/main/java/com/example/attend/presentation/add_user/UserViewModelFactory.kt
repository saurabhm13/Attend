package com.example.attend.presentation.add_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.attend.model.local.dao.RequestAbsenceDao
import com.example.attend.model.local.dao.UserDao
import kotlin.math.abs

class UserViewModelFactory(
    private val userDao: UserDao,
    private val absenceDao: RequestAbsenceDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(userDao, absenceDao) as T
    }
}