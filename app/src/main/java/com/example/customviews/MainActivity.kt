package com.example.customviews

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customviews.buttons.ButtonsActivity
import com.example.customviews.databinding.ActivityMainBinding
import com.example.customviews.tictactoe.TicTacToeField

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.toButtonsActivityButton.setOnClickListener {
            val intent = Intent(this, ButtonsActivity::class.java)
            startActivity(intent)
        }

        binding.toTicTacToeButton.setOnClickListener {
            val intent = Intent(this, TicTacToeField::class.java)
//            intent.putExtra("rows", 5)
//            intent.putExtra("columns", 5)
            startActivity(intent)
        }
    }
}