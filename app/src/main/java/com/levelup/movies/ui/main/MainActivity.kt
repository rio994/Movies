package com.levelup.movies.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.levelup.movies.R
import com.levelup.movies.ui.home.HomeFragment
import com.levelup.movies.util.navigateTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var exitFlag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.navigateTo(HomeFragment())
        button_home.setOnClickListener {
            supportFragmentManager.popBackStack("HOME", 0)
        }
    }

    override fun onBackPressed() {
        when{
            checkContainerStack() && exitFlag == 0 -> {
                Toast.makeText(this, "Press return one more time to exit", Toast.LENGTH_SHORT).show()
                exitFlag = 1
                Handler(Looper.getMainLooper()).postDelayed( { exitFlag = 0 }, 2000)
            }
            checkContainerStack() && exitFlag == 1 -> finish()

            else -> {
                exitFlag = 0
                supportFragmentManager.apply {
                    executePendingTransactions()
                    popBackStackImmediate()
                }
            }
        }
    }

    private fun checkContainerStack() = supportFragmentManager.backStackEntryCount == 1
            && supportFragmentManager.findFragmentById(R.id.fragment_container) is HomeFragment


}