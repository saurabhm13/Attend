package com.example.attend.presentation.add_edit_class

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attend.model.data.ClassEntity
import com.example.attend.model.local.dao.ClassDao
import kotlinx.coroutines.launch

class ClassViewModel(
    private val classDao: ClassDao
): ViewModel() {

    private var _classEditStatus = MutableLiveData<Boolean>()
    val classEditStatus: LiveData<Boolean> get() = _classEditStatus

    fun addEditClass(classEntity: ClassEntity) {
        try {
            viewModelScope.launch {
                classDao.upsertClass(classEntity)
            }
            _classEditStatus.value = true
        }catch (e: Exception) {
            _classEditStatus.value = false
        }
    }

    fun getClassById(classId: Long) = classDao.getClassById(classId)

    fun getAllClass() = classDao.getAllClasses()

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

}