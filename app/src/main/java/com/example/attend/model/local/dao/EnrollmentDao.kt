package com.example.attend.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attend.model.data.Enrollment

@Dao
interface EnrollmentDao {
    @Insert
    suspend fun insertEnrollment(enrollment: Enrollment)

    @Query("SELECT * FROM enrollment WHERE student_id = :studentId")
    suspend fun getEnrollmentsForStudent(studentId: Long): List<Enrollment>
}