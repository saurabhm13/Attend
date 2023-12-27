package com.example.attend.presentation.add_user

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.attend.databinding.ActivityAddUserBinding
import com.example.attend.model.data.User
import com.example.attend.model.local.AppDatabase
import com.example.attend.utils.Constants.Companion.DOB
import com.example.attend.utils.Constants.Companion.GENDER
import com.example.attend.utils.Constants.Companion.NAME
import com.example.attend.utils.Constants.Companion.PASSWORD
import com.example.attend.utils.Constants.Companion.STUDENT
import com.example.attend.utils.Constants.Companion.TEACHER
import com.example.attend.utils.Constants.Companion.USERNAME
import com.example.attend.utils.Constants.Companion.USER_ID
import com.example.attend.utils.Constants.Companion.USER_TYPE
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    private lateinit var userViewModel: UserViewModel

    private var userId: Long = 0
    private lateinit var username: String
    private lateinit var name: String
    private lateinit var gender: String
    private lateinit var dob: String
    private lateinit var password: String

    private var calendar = Calendar.getInstance()

    @SuppressLint("ResourceType")
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

        userViewModel.errorCallBack = {
            Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
        }

        val genderList = arrayOf("Male", "Female")

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderList)

        binding.genderSp.setAdapter(adapter)

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
            name = binding.name.editText?.text.toString().trim()
            gender = binding.gender.editText?.text.toString().trim()
            dob = binding.dateOfBirth.editText?.text.toString().trim()
            password = binding.password.editText?.text.toString().trim()

            if (username != "" && password != "" && name != "" && gender != "" && dob != "" && binding.id.editText?.text.toString() != "") {
                userId = binding.id.editText?.text.toString().trim().toLong()
                val user = User(userId, username, name, gender, dob, password, STUDENT)
                userViewModel.insertUser(user)
            }else {
                Toast.makeText(this, "Insert All Fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.saveAsTeacher.setOnClickListener {
            username = binding.username.editText?.text.toString().trim()
            password = binding.password.editText?.text.toString().trim()
            name = binding.name.editText?.text.toString().trim()
            gender = binding.gender.editText?.text.toString().trim()
            dob = binding.dateOfBirth.editText?.text.toString().trim()

            if (username != "" && password != "" && name != "" && gender != "" && dob != "" && binding.id.editText?.text.toString() != "") {
                userId = binding.id.editText?.text.toString().trim().toLong()
                val user = User(userId, username, name, gender, dob, password, TEACHER)
                userViewModel.insertUser(user)
            }else {
                Toast.makeText(this, "Insert All Fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.datePicker.setOnClickListener {
            showDatePickerDialog()
        }

        binding.delete.setOnClickListener {
            val user = User(userId, username, name, gender, dob, password, STUDENT)
            userViewModel.deleteUser(user)
            finish()
        }

        binding.back.setOnClickListener {
            finish()
        }


    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)

                binding.dateOfBirth.editText?.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun addDataFields() {
        userId = intent.getLongExtra(USER_ID, 0)
        username = intent.getStringExtra(USERNAME).toString()
        name = intent.getStringExtra(NAME).toString()
        gender = intent.getStringExtra(GENDER).toString()
        dob = intent.getStringExtra(DOB).toString()
        password = intent.getStringExtra(PASSWORD).toString()

        if (intent.getStringExtra(USER_TYPE) == STUDENT) {
            binding.saveAsTeacher.visibility = View.GONE
        }else {
            binding.saveAsStudent.visibility = View.GONE
        }

        binding.id.editText?.setText(userId.toString())
        binding.username.editText?.setText(username)
        binding.name.editText?.setText(name)
        binding.gender.editText?.setText(gender)
        binding.dateOfBirth.editText?.setText(dob)
        binding.password.editText?.setText(password)

        binding.id.editText?.isEnabled = false
    }

    private fun clearFields() {
        binding.id.editText?.setText("")
        binding.username.editText?.setText("")
        binding.name.editText?.setText("")
        binding.gender.editText?.setText("")
        binding.dateOfBirth.editText?.setText("")
        binding.password.editText?.setText("")
    }

}