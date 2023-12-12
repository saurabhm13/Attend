package com.example.attend.presentation.add_edit_class

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.attend.R
import com.example.attend.databinding.ActivityAddEditClassBinding
import com.example.attend.model.data.AttendanceReport
import com.example.attend.model.data.ClassEntity
import com.example.attend.model.local.AppDatabase
import com.example.attend.presentation.add_user.UserViewModel
import com.example.attend.presentation.add_user.UserViewModelFactory
import com.example.attend.utils.Constants.Companion.CLASS_ID
import com.example.attend.utils.Constants.Companion.CLASS_NAME
import com.example.attend.utils.Constants.Companion.DATE
import com.example.attend.utils.Constants.Companion.FROM
import com.example.attend.utils.Constants.Companion.TEACHER
import com.example.attend.utils.Constants.Companion.TO
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.xml.datatype.DatatypeConstants.MONTHS

class AddEditClassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditClassBinding
    private lateinit var classViewModel: ClassViewModel

    private var classId: Long = 0
    private lateinit var className: String
    private lateinit var teacher: String
    private lateinit var date: String
    private lateinit var from: String
    private lateinit var to: String

    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDao = AppDatabase.getInstance(applicationContext).userDao()
        val classDao = AppDatabase.getInstance(applicationContext).classDao()
        val enrollmentDao = AppDatabase.getInstance(applicationContext).enrollmentDao()
        val attendanceDao = AppDatabase.getInstance(applicationContext).attendanceDao()
        val attendanceReportDao = AppDatabase.getInstance(applicationContext).attendanceReportDao()

        classViewModel = ViewModelProvider(
            this,
            ClassViewModelFactory(classDao, userDao, enrollmentDao, attendanceDao, attendanceReportDao)
        )[ClassViewModel::class.java]

        if (intent.getStringExtra(CLASS_NAME) != null) {
            addDataFields()
            binding.delete.visibility = View.VISIBLE
        }

        binding.save.setOnClickListener {
            className = binding.className.editText?.text.toString().trim()
            teacher = binding.teacher.editText?.text.toString().trim()
            date = binding.date.editText?.text.toString().trim()
            from = binding.from.editText?.text.toString().trim()
            to = binding.to.editText?.text.toString().trim()

            if (className.isNotBlank() && teacher.isNotBlank() && date.isNotBlank() && from.isNotBlank() && to.isNotBlank()) {
                val classEntry = ClassEntity(classId, className, teacher, date, from, to)
                classViewModel.addEditClass(classEntry)
            } else {
                Toast.makeText(this, "Add all fields", Toast.LENGTH_SHORT).show()
            }
        }

        classViewModel.classEditStatus.observe(this) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show()
            }
        }

        binding.datePicker.setOnClickListener {
            showDatePickerDialog()
        }

        binding.fromTimePicker.setOnClickListener {
            showTimePickerDialog(FROM)
        }

        binding.toTimePicker.setOnClickListener {
            showTimePickerDialog(TO)
        }

        binding.delete.setOnClickListener {
            val classEntry = ClassEntity(classId, className, teacher, date, from, to)
            classViewModel.deleteClass(classEntry)
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

                binding.date.editText?.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog(setText: String) {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)

                if (setText == FROM) {
                    binding.from.editText?.setText(selectedTime)
                } else {
                    binding.to.editText?.setText(selectedTime)
                }
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun addDataFields() {
        classId = intent.getLongExtra(CLASS_ID, 0)
        className = intent.getStringExtra(CLASS_NAME).toString()
        teacher = intent.getStringExtra(TEACHER).toString()
        date = intent.getStringExtra(DATE).toString()
        from = intent.getStringExtra(FROM).toString()
        to = intent.getStringExtra(TO).toString()

        binding.className.editText?.setText(className)
        binding.teacher.editText?.setText(teacher)
        binding.date.editText?.setText(date)
        binding.from.editText?.setText(from)
        binding.to.editText?.setText(to)

        binding.className.editText?.inputType = InputType.TYPE_NULL
        binding.teacher.editText?.inputType = InputType.TYPE_NULL
    }

    private fun clearFields() {
        binding.className.editText?.setText("")
        binding.teacher.editText?.setText("")
        binding.date.editText?.setText("")
        binding.from.editText?.setText("")
        binding.to.editText?.setText("")
    }
}