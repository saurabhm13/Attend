package com.example.attend.presentation.add_user

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attend.model.data.RequestAbsence
import com.example.attend.model.data.User
import com.example.attend.model.local.dao.RequestAbsenceDao
import com.example.attend.model.local.dao.UserDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class UserViewModel(
    private val userDao: UserDao,
    private val requestAbsenceDao: RequestAbsenceDao
): ViewModel() {

    private var _userInsertStatus = MutableLiveData<Boolean>()
    val userInsertStatus: LiveData<Boolean> get() = _userInsertStatus

    var successCallBack: (() -> Unit)? = null
    var errorCallBack: ((String) -> Unit)? = null

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

    fun insertAbsence(title: String, reason: String, studentId: Long) {
        try {
            viewModelScope.launch {
                val absence = RequestAbsence(0, studentId, getCurrentDate(), reason, title)
                requestAbsenceDao.insertExcusedAbsenceRequest(absence)
                successCallBack?.invoke()
            }
        }catch (e: Exception) {
            errorCallBack?.invoke(e.message.toString())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        return formatter.format(time)
    }

}