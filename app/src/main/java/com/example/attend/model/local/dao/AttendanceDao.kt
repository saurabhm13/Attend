package com.example.attend.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attend.model.data.Attendance

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insertAttendance(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE student_id = :studentId")
    fun getAttendanceForStudent(studentId: Long): LiveData<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE class_id = :classId")
    fun getClassAttendance(classId: Long): LiveData<List<Attendance>>

    @Query("DELETE FROM attendance WHERE student_id = :studentId AND class_id = :classId")
    suspend fun removeAttendanceOfStudentFromClass(studentId: Long, classId: Long)
}
