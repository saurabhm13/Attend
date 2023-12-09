package com.example.attend.presentation.admin_home.user_management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attend.R
import com.example.attend.databinding.FragmentStudentManagementBinding
import com.example.attend.databinding.FragmentTeacherManagementBinding
import com.example.attend.model.local.AppDatabase
import com.example.attend.presentation.adapter.UserAdapter
import com.example.attend.presentation.add_user.UserViewModel
import com.example.attend.presentation.add_user.UserViewModelFactory
import com.example.attend.utils.Constants
import com.example.attend.utils.Constants.Companion.TEACHER

class TeacherManagementFragment : Fragment() {

    private lateinit var binding: FragmentTeacherManagementBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeacherManagementBinding.inflate(layoutInflater, container, false)

        val userDao = AppDatabase.getInstance(requireContext()).userDao()

        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userDao)
        )[UserViewModel::class.java]

        prepareRecyclerView()

        return binding.root
    }

    private fun prepareRecyclerView() {
        val userAdapter = UserAdapter()

        binding.rvTeacher.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
        }

        userViewModel.getUserByUserType(TEACHER).observe(viewLifecycleOwner) {
            userAdapter.setUserList(it)
        }
    }

}