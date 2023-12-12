package com.example.attend.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attend.databinding.ItemAttendanceBinding
import com.example.attend.model.data.AttendanceReport
import com.example.attend.presentation.add_edit_class.ClassViewModel

class AttendanceReportAdapter(): RecyclerView.Adapter<AttendanceReportAdapter.AttendanceViewHolder>() {

    private var attendanceReportList = ArrayList<AttendanceReport>()

    @SuppressLint("NotifyDataSetChanged")
    fun setAttendanceList(attendanceList: List<AttendanceReport>) {
        this.attendanceReportList.clear()
        this.attendanceReportList.addAll(attendanceList)
        notifyDataSetChanged()
    }

    class AttendanceViewHolder(val binding: ItemAttendanceBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        return AttendanceViewHolder(ItemAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return attendanceReportList.size
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        holder.binding.className.text = attendanceReportList[position].className
        holder.binding.teacher.text = attendanceReportList[position].teacher
        holder.binding.present.text = attendanceReportList[position].present.toString()
        holder.binding.absence.text = attendanceReportList[position].absence.toString()
        holder.binding.tardy.text = attendanceReportList[position].tardy.toString()
    }

}