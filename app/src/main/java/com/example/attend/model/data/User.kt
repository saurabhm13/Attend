package com.example.attend.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val user_id: Long,
    val username: String,
    val name: String,
    val gender: String,
    val dob: String,
    val password: String,
    val user_type: String
)
