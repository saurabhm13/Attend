package com.example.attend.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attend.databinding.ItemAddStudentBinding
import com.example.attend.databinding.ItemClassBinding
import com.example.attend.model.data.ClassEntity
import com.example.attend.model.data.User

class AddStudentsInClassAdapter(
    private val onAddClick: ((user: User, isCheck: Boolean) -> Unit)
): RecyclerView.Adapter<AddStudentsInClassAdapter.EnrolledStudentViewHolder>() {

    private var allStudentsList = ArrayList<User>()
    private var enrolledStudentsList = ArrayList<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun setAllStudentList(classList: List<User>) {
        this.allStudentsList.clear()
        this.allStudentsList.addAll(classList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setEnrolledStudentList(enrolledStudentList: List<User>) {
        this.enrolledStudentsList.clear()
        this.enrolledStudentsList.addAll(enrolledStudentList)
        notifyDataSetChanged()
    }

    class EnrolledStudentViewHolder(val binding: ItemAddStudentBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnrolledStudentViewHolder {
        return EnrolledStudentViewHolder(ItemAddStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return allStudentsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EnrolledStudentViewHolder, position: Int) {
        holder.binding.student.text = allStudentsList[position].username
        holder.binding.check.isChecked = enrolledStudentsList.contains(allStudentsList[position])

        holder.binding.check.setOnClickListener {
            val isCheck = holder.binding.check.isChecked
            if (isCheck) {
                onAddClick.invoke(allStudentsList[position], true)
            }else {
                onAddClick.invoke(allStudentsList[position], false)
            }

        }


    }
}