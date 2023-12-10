package com.example.attend.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.attend.model.data.User

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: User)

    @Query("SELECT * FROM user WHERE user_id = :userId")
    fun getUserById(userId: Long): LiveData<User>

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<User>

    @Query("SELECT * FROM user WHERE user_type = :userType")
    fun getUserByType(userType: String): LiveData<List<User>>

    @Delete
    suspend fun deleteUser(user: User)
}