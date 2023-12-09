package com.example.attend.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attend.model.data.ClassEntity

@Dao
interface ClassDao {
    @Insert
    suspend fun insertClass(classEntity: ClassEntity)

    @Query("SELECT * FROM class WHERE class_id = :classId")
    suspend fun getClassById(classId: Long): ClassEntity?

    @Query("SELECT * FROM class")
    suspend fun getAllClasses(): List<ClassEntity>
}
