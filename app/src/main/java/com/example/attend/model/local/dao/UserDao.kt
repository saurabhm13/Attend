package com.example.attend.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attend.model.data.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE user_id = :userId")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?
}