package com.example.attend.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.attend.R
import com.example.attend.databinding.ItemStudentAttendanceBinding
import com.example.attend.model.data.Attendance
import com.example.attend.model.data.User
import com.example.attend.utils.Constants.Companion.ABSENT
import com.example.attend.utils.Constants.Companion.PRESENT
import com.example.attend.utils.Constants.Companion.TARDY

class StudentAttendanceAdapter(
    private val onPresentClick: ((User) -> Unit),
    private val onAbsentClick: ((User) -> Unit),
    private val onTardyClick: ((User) -> Unit),
    private val onAttendanceStatusClick: ((user: User, status: String) -> Unit),
): RecyclerView.Adapter<StudentAttendanceAdapter.StudentAttendanceViewHolder>() {

    private var studentList = ArrayList<User>()
    private var attendanceList = ArrayList<Attendance>()

    @SuppressLint("NotifyDataSetChanged")
    fun setStudentList(studentList: List<User>) {
        this.studentList.clear()
        this.studentList.addAll(studentList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAttendanceList(attendanceList: List<Attendance>) {
        this.attendanceList.clear()
        this.attendanceList.addAll(attendanceList)
        notifyDataSetChanged()
    }

    class StudentAttendanceViewHolder(val binding: ItemStudentAttendanceBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAttendanceViewHolder {
        return StudentAttendanceViewHolder(ItemStudentAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: StudentAttendanceViewHolder, position: Int) {
        holder.binding.student.text = studentList[position].username

        val attendanceStatus = getAttendanceStatus(studentList[position].user_id)

        if (attendanceStatus != null) {
            showAttendanceStatus(holder)
            when (attendanceStatus) {
                PRESENT -> {
                    holder.binding.attendanceStatus.text = "P"
                    holder.binding.attendanceStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.darkGreen))
                    holder.binding.attendanceStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.lightGreen))
                }
                ABSENT -> {
                    holder.binding.attendanceStatus.text = "A"
                    holder.binding.attendanceStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.darkRed))
                    holder.binding.attendanceStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.lightRed))
                }
                TARDY -> {
                    holder.binding.attendanceStatus.text = "T"
                    holder.binding.attendanceStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.darkBlue))
                    holder.binding.attendanceStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.lightBlue))
                }
            }
        }else {
            hideAttendanceStatus(holder)
        }

        holder.binding.present.setOnClickListener {
            onPresentClick.invoke(studentList[position])
            holder.binding.attendanceStatus.text = "P"
        }

        holder.binding.absent.setOnClickListener {
            onAbsentClick.invoke(studentList[position])
            holder.binding.attendanceStatus.text = "A"
        }

        holder.binding.tardy.setOnClickListener {
            onTardyClick.invoke(studentList[position])
            holder.binding.attendanceStatus.text = "T"
        }

        holder.binding.attendanceStatus.setOnClickListener {
            when (holder.binding.attendanceStatus.text.toString()) {
                "P" -> {
                    onAttendanceStatusClick.invoke(studentList[position], PRESENT)
                }
                "A" -> {
                    onAttendanceStatusClick.invoke(studentList[position], ABSENT)
                }
                else -> {
                    onAttendanceStatusClick.invoke(studentList[position], TARDY)
                }
            }
        }
    }

    private fun getAttendanceStatus(userId: Long): String? {
        val attendance = attendanceList.find { it.student_id == userId }
        return attendance?.attendance_status
    }

    private fun showAttendanceStatus(holder: StudentAttendanceViewHolder) {
        holder.binding.attendanceStatus.visibility = View.VISIBLE
        holder.binding.present.visibility = View.GONE
        holder.binding.absent.visibility = View.GONE
        holder.binding.tardy.visibility = View.GONE
    }

    private fun hideAttendanceStatus(holder: StudentAttendanceViewHolder) {
        holder.binding.attendanceStatus.visibility = View.GONE
        holder.binding.present.visibility = View.VISIBLE
        holder.binding.absent.visibility = View.VISIBLE
        holder.binding.tardy.visibility = View.VISIBLE
    }

}