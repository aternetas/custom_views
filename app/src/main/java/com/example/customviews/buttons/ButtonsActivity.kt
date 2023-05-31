package com.example.customviews.buttons

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.customviews.databinding.ActivityButtonsBinding

class ButtonsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityButtonsBinding
    private val token = Any()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        setupActionListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(token)
    }

    private fun setupActionListeners() {
        binding.bottomButtons.setListener {
            when (it) {
                BottomButtonsAction.POSITIVE -> {
                    binding.bottomButtons.isProgressMode = true
                    handler.postDelayed({
                        binding.bottomButtons.isProgressMode = false
                        binding.bottomButtons.setPositiveButtonText("New Ok")
                        Toast.makeText(this, "positive", Toast.LENGTH_SHORT).show()
                    }, token, 2000)
                }

                BottomButtonsAction.NEGATIVE -> {
                    binding.bottomButtons.setNegativeButtonText("New No")
                    Toast.makeText(this, "negative", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}