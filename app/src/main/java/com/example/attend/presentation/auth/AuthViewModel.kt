package com.example.attend.presentation.auth

import androidx.lifecycle.ViewModel
import com.example.attend.model.local.dao.UserDao

class AuthViewModel(
    private val userDao: UserDao
): ViewModel() {

    fun getUserByUserName(username: String) = userDao.getUserByUsername(username)

}