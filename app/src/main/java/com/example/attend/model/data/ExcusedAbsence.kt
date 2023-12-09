package com.example.attend.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "excuseAbsence",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["student_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ClassEntity::class,
            parentColumns = ["class_id"],
            childColumns = ["class_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class ExcusedAbsence(
    @PrimaryKey(autoGenerate = true)
    val request_id: Long,
    val student_id: Long,
    val class_id: Long,
    val request_date: String,
    val reason: String,
    val title: String,
    val status: String
)
