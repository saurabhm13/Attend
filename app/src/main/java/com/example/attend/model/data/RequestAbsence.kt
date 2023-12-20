package com.example.attend.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "requestAbsence",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["student_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class RequestAbsence(
    @PrimaryKey(autoGenerate = true)
    val request_id: Long,
    val student_id: Long,
    val request_date: String,
    val reason: String,
    val title: String,
)
