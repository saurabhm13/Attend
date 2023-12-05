package com.example.attend.presentation.student_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.attend.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeStudentActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_student)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.studentBottomNavigation)
        navController = Navigation.findNavController(this, R.id.host_fragment)

        bottomNavigation.setupWithNavController(navController)

    }
}