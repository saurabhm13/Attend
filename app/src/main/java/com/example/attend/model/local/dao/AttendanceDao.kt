package com.example.attend.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attend.model.data.Attendance

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insertAttendance(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE class_id = :classId AND student_id = :studentId")
    suspend fun getAttendanceForStudentInClass(classId: Long, studentId: Long): List<Attendance>
}
