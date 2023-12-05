package com.example.attend.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.attend.presentation.admin_home.user_management.StudentManagementFragment
import com.example.attend.presentation.admin_home.user_management.TeacherManagementFragment

class UserManagementVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StudentManagementFragment()
            1 -> TeacherManagementFragment()
//            2 -> DeliveredOrderFragment()
            // Add more cases for additional tabs/fragments
            else -> StudentManagementFragment()
        }
    }

}