package com.example.attend.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "enrollment",
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
    ]
)
data class Enrollment(
    @PrimaryKey(autoGenerate = true)
    val enrollment_id: Long = 0,
    val student_id: Long,
    val class_id: Long
)
