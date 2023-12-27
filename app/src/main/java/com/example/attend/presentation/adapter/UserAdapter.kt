package com.example.attend.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attend.databinding.ItemUserBinding
import com.example.attend.model.data.ClassEntity
import com.example.attend.model.data.User

class UserAdapter(
    private val onItemClick: ((User) -> Unit)
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList = ArrayList<User>()
    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(userList: List<User>) {
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    class UserViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.user.text = userList[position].name

        holder.binding.root.setOnClickListener {
            onItemClick.invoke(userList[position])
        }
    }
}