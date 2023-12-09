package com.example.attend.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val user_id: Long = 0,
    val username: String,
    val password: String,
    val user_type: String
)
