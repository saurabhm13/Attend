package com.example.attend.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attend.databinding.ItemClassBinding
import com.example.attend.databinding.ItemUserBinding
import com.example.attend.model.data.ClassEntity
import com.example.attend.model.data.User

class ClassAdapter(
    private val onItemClick: ((ClassEntity) -> Unit),
    private val onEditClick: ((ClassEntity) -> Unit)
): RecyclerView.Adapter<ClassAdapter.ClassViewHolder>() {

    private var classList = ArrayList<ClassEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setClassList(classList: List<ClassEntity>) {
        this.classList.clear()
        this.classList.addAll(classList)
        notifyDataSetChanged()
    }

    class ClassViewHolder(val binding: ItemClassBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        return ClassViewHolder(ItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return classList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.binding.className.text = classList[position].class_name
        holder.binding.teacherName.text = classList[position].teacher
        holder.binding.date.text = classList[position].date
        holder.binding.time.text = classList[position].from + " - " + classList[position].to

        holder.binding.root.setOnClickListener {
            onItemClick.invoke(classList[position])
        }

        holder.binding.edit.setOnClickListener {
            onEditClick.invoke(classList[position])
        }
    }
}