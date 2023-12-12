package com.example.attend.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.attend.model.data.AttendanceReport

@Dao
interface AttendanceReportDao {

    @Upsert
    suspend fun upsertAttendanceReport(attendanceReport: AttendanceReport)

    @Insert
    suspend fun insertAttendanceReport(attendanceReport: AttendanceReport)

    @Query("SELECT * FROM attendanceReport WHERE class_id = :classId")
    fun getAttendanceReportForClass(classId: Long): LiveData<AttendanceReport>

    @Query("SELECT * FROM attendanceReport")
    fun getAllAttendanceReport(): LiveData<List<AttendanceReport>>

    @Query("SELECT * FROM attendanceReport WHERE teacher = :teacher")
    fun getAttendanceReportForTeacher(teacher: String): LiveData<List<AttendanceReport>>

    @Query("DELETE FROM attendanceReport WHERE class_id = :classId")
    suspend fun deleteAttendanceReport(classId: Long)

}