package com.example.attend.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attend.model.data.ExcusedAbsence

@Dao
interface ExcusedAbsenceDao {
    @Insert
    suspend fun insertExcusedAbsenceRequest(request: ExcusedAbsence)

    @Query("SELECT * FROM excuseAbsence WHERE student_id = :studentId")
    fun getExcusedAbsenceRequestsForStudent(studentId: Long): LiveData<List<ExcusedAbsence>>
}
