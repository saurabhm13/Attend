package com.example.attend.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.attend.R
import com.example.attend.databinding.ActivityLoginBinding
import com.example.attend.model.local.AppDatabase
import com.example.attend.presentation.add_user.UserViewModel
import com.example.attend.presentation.add_user.UserViewModelFactory
import com.example.attend.presentation.admin_home.HomeAdminActivity
import com.example.attend.presentation.student_home.HomeStudentActivity
import com.example.attend.presentation.teacher_home.HomeTeacherActivity
import com.example.attend.utils.Constants.Companion.ADMIN
import com.example.attend.utils.Constants.Companion.NAME
import com.example.attend.utils.Constants.Companion.STUDENT
import com.example.attend.utils.Constants.Companion.TEACHER
import com.example.attend.utils.Constants.Companion.USER_ID
import com.example.attend.utils.Constants.Companion.USER_TYPE

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var userType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userType = intent.getStringExtra(USER_TYPE).toString()

        val userDao = AppDatabase.getInstance(this).userDao()

        authViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(userDao)
        )[AuthViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            username = binding.username.editText?.text.toString().trim()
            password = binding.password.editText?.text.toString().trim()

            if (username != "" && password != "") {

                if (userType == ADMIN) {
                    if (username == "admin" && password == "admin") {
                        val intoAdmin = Intent(this, HomeAdminActivity::class.java)
                        intoAdmin.putExtra(USER_TYPE, ADMIN)
                        startActivity(intoAdmin)
                        finish()
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(this, "Invalid User", Toast.LENGTH_SHORT).show()
                    }
                }else {
                    authViewModel.getUserByUserName(username).observe(this) {
                        if (it != null && username == it.username && password == it.password && userType == it.user_type) {
                            when (userType) {
                                STUDENT -> {
                                    val intoStudent = Intent(this, HomeStudentActivity::class.java)
                                    intoStudent.putExtra(USER_TYPE, STUDENT)
                                    intoStudent.putExtra(USER_ID, it.user_id)
                                    intoStudent.putExtra(NAME, it.name)
                                    startActivity(intoStudent)
                                    finish()
                                }
                                TEACHER -> {
                                    val intoTeacher = Intent(this, HomeTeacherActivity::class.java)
                                    intoTeacher.putExtra(USER_TYPE, TEACHER)
                                    intoTeacher.putExtra(NAME, it.name)
                                    startActivity(intoTeacher)
                                    finish()
                                }
                            }
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(this, "Invalid User", Toast.LENGTH_SHORT).show()
                        }
                    }
                }


            }else {
                Toast.makeText(this, "Add All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}