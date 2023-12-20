package com.example.attend.presentation.add_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.attend.R
import com.example.attend.databinding.ActivityAddUserBinding
import com.example.attend.model.data.User
import com.example.attend.model.local.AppDatabase
import com.example.attend.utils.Constants.Companion.PASSWORD
import com.example.attend.utils.Constants.Companion.STUDENT
import com.example.attend.utils.Constants.Companion.TEACHER
import com.example.attend.utils.Constants.Companion.USERNAME
import com.example.attend.utils.Constants.Companion.USER_ID
import com.example.attend.utils.Constants.Companion.USER_TYPE

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    private lateinit var userViewModel: UserViewModel

    private var user_id: Long = 0
    private lateinit var username: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDao = AppDatabase.getInstance(this).userDao()
        val absenceDao = AppDatabase.getInstance(this).excuseAbsenceDao()

        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userDao, absenceDao)
        )[UserViewModel::class.java]

        if (intent.getStringExtra(USERNAME) != null) {
            addDataFields()
            binding.delete.visibility = View.VISIBLE
        }

        userViewModel.userInsertStatus.observe(this) {isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                clearFields()
            }else {
                Toast.makeText(this, "Error Occurred. Try again", Toast.LENGTH_SHORT).show()
            }
        }

        binding.saveAsStudent.setOnClickListener {
            username = binding.username.editText?.text.toString().trim()
            password = binding.password.editText?.text.toString().trim()

            if (username != "" && password != "") {
                val user = User(user_id, username, password, STUDENT)
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

        binding.delete.setOnClickListener {
            val user = User(user_id, username, password, STUDENT)
            userViewModel.deleteUser(user)
            finish()
        }

        binding.back.setOnClickListener {
            finish()
        }


    }

    private fun addDataFields() {
        user_id = intent.getLongExtra(USER_ID, 0)
        username = intent.getStringExtra(USERNAME).toString()
        password = intent.getStringExtra(PASSWORD).toString()

        if (intent.getStringExtra(USER_TYPE) == STUDENT) {
            binding.saveAsTeacher.visibility = View.GONE
        }else {
            binding.saveAsStudent.visibility = View.GONE
        }

        binding.username.editText?.setText(username)
        binding.password.editText?.setText(password)
    }

    private fun clearFields() {
        binding.username.editText?.setText("")
        binding.password.editText?.setText("")
    }

}