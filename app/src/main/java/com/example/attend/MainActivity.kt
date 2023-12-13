package com.example.attend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.attend.databinding.ActivityMainBinding
import com.example.attend.presentation.admin_home.HomeAdminActivity
import com.example.attend.presentation.auth.LoginActivity
import com.example.attend.presentation.student_home.HomeStudentActivity
import com.example.attend.presentation.teacher_home.HomeTeacherActivity
import com.example.attend.utils.Constants.Companion.ADMIN
import com.example.attend.utils.Constants.Companion.STUDENT
import com.example.attend.utils.Constants.Companion.TEACHER
import com.example.attend.utils.Constants.Companion.USER_TYPE

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvAdmin.setOnClickListener {
            val intoLogin = Intent(this, LoginActivity::class.java)
            intoLogin.putExtra(USER_TYPE, ADMIN)
            startActivity(intoLogin)
        }

        binding.cvTeacher.setOnClickListener {
            val intoLogin = Intent(this, LoginActivity::class.java)
            intoLogin.putExtra(USER_TYPE, TEACHER)
            startActivity(intoLogin)
        }

        binding.cvStudent.setOnClickListener {
            val intoLogin = Intent(this, LoginActivity::class.java)
            intoLogin.putExtra(USER_TYPE, STUDENT)
            startActivity(intoLogin)
        }

    }
}