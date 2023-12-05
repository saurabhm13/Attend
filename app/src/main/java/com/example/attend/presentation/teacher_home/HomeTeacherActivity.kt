package com.example.attend.presentation.teacher_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.attend.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeTeacherActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_teacher)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.teacherBottomNavigation)
        navController = Navigation.findNavController(this, R.id.host_fragment)

        bottomNavigation.setupWithNavController(navController)
    }
}