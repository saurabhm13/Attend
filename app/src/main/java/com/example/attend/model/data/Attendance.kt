package com.example.attend.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "attendance",
    foreignKeys = [
        ForeignKey(
            entity = ClassEntity::class,
            parentColumns = ["class_id"],
            childColumns = ["class_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["student_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val attendance_id: Long = 0,
    val class_id: Long,
    val student_id: Long,
    val attendance_status: String,
    val attendance_date: String
)