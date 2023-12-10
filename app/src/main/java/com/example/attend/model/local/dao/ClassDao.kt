package com.example.attend.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.attend.model.data.ClassEntity

@Dao
interface ClassDao {
    @Upsert
    suspend fun upsertClass(classEntity: ClassEntity)

    @Query("SELECT * FROM class WHERE class_id = :classId")
    fun getClassById(classId: Long): LiveData<ClassEntity>

    @Query("SELECT * FROM class")
    fun getAllClasses(): LiveData<List<ClassEntity>>

    @Delete
    suspend fun deleteClass(classEntity: ClassEntity)
}
