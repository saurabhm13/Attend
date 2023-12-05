package com.example.attend.presentation.admin_home.user_management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.attend.R
import com.example.attend.databinding.FragmentUserManagementBinding
import com.example.attend.presentation.adapter.UserManagementVPAdapter
import com.google.android.material.tabs.TabLayoutMediator

class UserManagementFragment : Fragment() {

    private lateinit var binding: FragmentUserManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserManagementBinding.inflate(layoutInflater, container, false)

        val pagerAdapter = UserManagementVPAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position){
                0 -> tab.text = "Students"
                1 -> tab.text = "Teachers"
            }
        }.attach()


        return binding.root
    }

}