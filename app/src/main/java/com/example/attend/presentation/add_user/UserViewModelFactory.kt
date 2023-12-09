package com.example.attend.presentation.add_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.attend.model.local.dao.UserDao

class UserViewModelFactory(
    private val userDao: UserDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(userDao) as T
    }
}