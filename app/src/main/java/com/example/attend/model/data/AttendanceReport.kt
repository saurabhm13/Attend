package com.example.attend.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "attendanceReport",
    foreignKeys = [
        ForeignKey(
            entity = ClassEntity::class,
            parentColumns = ["class_id"],
            childColumns = ["class_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AttendanceReport(
    @PrimaryKey(autoGenerate = true)
    val attendance_report_id: Long = 0,
    val class_id: Long,
    val className: String,
    val teacher: String,
    val present: Int,
    val absence: Int,
    val tardy: Int,
)