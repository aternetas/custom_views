package com.example.customviews.bottombuttons

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.customviews.databinding.ActivityBottomButtonsBinding

class BottomButtonsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomButtonsBinding

    private val token = Any()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomButtonsBinding.inflate(layoutInflater).also { setContentView(it.root) }

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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        handler.postDelayed({
                            binding.bottomButtons.isProgressMode = false
                            binding.bottomButtons.setPositiveButtonText("New Ok")
                            Toast.makeText(this, "positive", Toast.LENGTH_SHORT).show()
                        }, token, 2000)
                    }
                }

                BottomButtonsAction.NEGATIVE -> {
                    binding.bottomButtons.setNegativeButtonText("New No")
                    Toast.makeText(this, "negative", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}