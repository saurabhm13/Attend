package com.example.attend.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attend.model.data.RequestAbsence

@Dao
interface RequestAbsenceDao {
    @Insert
    suspend fun insertExcusedAbsenceRequest(request: RequestAbsence)

    @Query("SELECT * FROM requestAbsence WHERE student_id = :studentId")
    fun getExcusedAbsenceRequestsForStudent(studentId: Long): LiveData<List<RequestAbsence>>
}
