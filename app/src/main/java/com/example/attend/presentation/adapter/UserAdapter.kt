package com.example.attend.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attend.databinding.ItemUserBinding
import com.example.attend.model.data.ClassEntity
import com.example.attend.model.data.User

class UserAdapter(): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList = ArrayList<User>()
    private var classList = ArrayList<ClassEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(userList: List<User>) {
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setClassList(classList: List<ClassEntity>) {
        this.classList.clear()
        this.classList.addAll(classList)
        notifyDataSetChanged()
    }

    class UserViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return if (userList.isNotEmpty()) {
            userList.size
        }else {
            classList.size
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (userList.isNotEmpty()) {
            holder.binding.user.text = userList[position].username
        }else {
            holder.binding.user.text = classList[position].class_name
        }

    }

}