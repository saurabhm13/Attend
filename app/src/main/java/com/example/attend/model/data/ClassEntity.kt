package com.example.attend.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "class")
data class ClassEntity(
    @PrimaryKey(autoGenerate = true)
    val class_id: Long = 0,
    val class_name: String,
    val teacher: String,
    val date: String,
    val from: String,
    val to: String
)
