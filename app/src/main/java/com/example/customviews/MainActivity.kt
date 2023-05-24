package com.example.customviews

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.customviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        setupUi()

        with(binding.bottomButtons) {
            positiveButton.setOnClickListener {
                negativeButton.visibility = View.GONE
                positiveButton.visibility = View.GONE
                progress.visibility = View.VISIBLE
            }
        }
    }

    private fun setupUi() = with(binding.bottomButtons) {
        progress.visibility = View.GONE
        negativeButton.setBackgroundColor(
            ContextCompat.getColor(applicationContext, R.color.red_200)
        )
    }
}