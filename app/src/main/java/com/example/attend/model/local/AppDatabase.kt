package com.example.attend.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.attend.model.data.Attendance
import com.example.attend.model.data.ClassEntity
import com.example.attend.model.data.Enrollment
import com.example.attend.model.data.ExcusedAbsence
import com.example.attend.model.data.User
import com.example.attend.model.local.dao.AttendanceDao
import com.example.attend.model.local.dao.ClassDao
import com.example.attend.model.local.dao.EnrollmentDao
import com.example.attend.model.local.dao.ExcusedAbsenceDao
import com.example.attend.model.local.dao.UserDao

@Database(
    entities = [User::class, ClassEntity::class, Enrollment::class, Attendance::class, ExcusedAbsence::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun classDao(): ClassDao
    abstract fun enrollmentDao(): EnrollmentDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun excuseAbsenceDao(): ExcusedAbsenceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context,
//                    AppDatabase::class.java,
//                    "app_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }

        @Synchronized
        fun getInstance(context: Context): AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE as AppDatabase
        }
    }
}
