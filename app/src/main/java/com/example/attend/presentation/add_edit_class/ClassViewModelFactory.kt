package com.example.attend.presentation.add_edit_class

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.attend.model.local.dao.ClassDao
import com.example.attend.model.local.dao.UserDao

class ClassViewModelFactory(
    private val classDao: ClassDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ClassViewModel(classDao) as T
    }
}