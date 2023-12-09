package com.example.attend.presentation.add_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.attend.R
import com.example.attend.databinding.ActivityAddUserBinding
import com.example.attend.model.data.User
import com.example.attend.model.local.AppDatabase
import com.example.attend.utils.Constants.Companion.STUDENT
import com.example.attend.utils.Constants.Companion.TEACHER

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    private lateinit var userViewModel: UserViewModel

    private lateinit var username: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDao = AppDatabase.getInstance(this).userDao()

        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userDao)
        )[UserViewModel::class.java]

        userViewModel.userInsertStatus.observe(this) {isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
                clearFields()
            }else {
                Toast.makeText(this, "Error Occurred. Try again", Toast.LENGTH_SHORT).show()
            }
        }

        binding.saveAsStudent.setOnClickListener {
            username = binding.username.editText?.text.toString().trim()
            password = binding.password.editText?.text.toString().trim()

            if (username != "" && password != "") {
                val user = User(0, username, password, STUDENT)
                userViewModel.insertUser(user)
            }else {
                Toast.makeText(this, "Insert All Fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.saveAsTeacher.setOnClickListener {
            username = binding.username.editText?.text.toString().trim()
            password = binding.password.editText?.text.toString().trim()

            if (username != "" && password != "") {
                val user = User(0, username, password, TEACHER)
                userViewModel.insertUser(user)
            }else {
                Toast.makeText(this, "Insert All Fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.back.setOnClickListener {
            finish()
        }


    }

    private fun clearFields() {
        binding.username.editText?.setText("")
        binding.password.editText?.setText("")
    }

}