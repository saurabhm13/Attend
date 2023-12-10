package com.example.attend.presentation.add_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attend.model.data.User
import com.example.attend.model.local.dao.UserDao
import kotlinx.coroutines.launch

class UserViewModel(
    private val userDao: UserDao
): ViewModel() {

    private var _userInsertStatus = MutableLiveData<Boolean>()
    val userInsertStatus: LiveData<Boolean> get() = _userInsertStatus

    fun insertUser(user: User) {
        viewModelScope.launch {
            try {
                userDao.upsertUser(user)
                _userInsertStatus.value = true
            }catch (e: Exception) {
                _userInsertStatus.value = false
            }

        }
    }

    // Get a user by ID from the database
    fun getUserById(userId: Long) = userDao.getUserById(userId)

    // Get a user by username from the database
    fun getUserByUsername(username: String) = userDao.getUserByUsername(username)

    fun getUserByUserType(userType: String) = userDao.getUserByType(userType)

    fun deleteUser(user: User) {
        try {
            viewModelScope.launch {
                userDao.deleteUser(user)
                _userInsertStatus.value = true
            }
        }catch (e: Exception) {
            _userInsertStatus.value = false
        }

    }

}