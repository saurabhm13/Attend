package com.example.attend.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.attend.model.data.Enrollment
import com.example.attend.model.data.User

@Dao
interface EnrollmentDao {
    @Upsert
    suspend fun upsertEnrollment(enrollment: Enrollment)

    @Query("SELECT * FROM enrollment WHERE student_id = :studentId")
    fun getEnrollmentsForStudent(studentId: Long): LiveData<List<Enrollment>>

    @Query("SELECT user.* FROM user INNER JOIN enrollment ON user.user_id = enrollment.student_id WHERE enrollment.class_id = :classId")
    fun getEnrolledStudentsInClass(classId: Long): LiveData<List<User>>

    @Query("DELETE FROM enrollment WHERE student_id = :studentId AND class_id = :classId")
    suspend fun removeStudentFromClass(studentId: Long, classId: Long)
}