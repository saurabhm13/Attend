package com.example.attend.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attend.model.data.Enrollment

@Dao
interface EnrollmentDao {
    @Insert
    suspend fun insertEnrollment(enrollment: Enrollment)

    @Query("SELECT * FROM enrollment WHERE student_id = :studentId")
    fun getEnrollmentsForStudent(studentId: Long): LiveData<List<Enrollment>>
}