package com.example.attend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.attend.databinding.ActivityMainBinding
import com.example.attend.presentation.admin_home.HomeAdminActivity
import com.example.attend.presentation.student_home.HomeStudentActivity
import com.example.attend.presentation.teacher_home.HomeTeacherActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvAdmin.setOnClickListener {
            val intoAdminHome = Intent(this, HomeAdminActivity::class.java)
            startActivity(intoAdminHome)
        }

        binding.cvTeacher.setOnClickListener {
            val intoTeacherHome = Intent(this, HomeTeacherActivity::class.java)
            startActivity(intoTeacherHome)
        }

        binding.cvStudent.setOnClickListener {
            val intoStudentHome = Intent(this, HomeStudentActivity::class.java)
            startActivity(intoStudentHome)
        }

    }
}