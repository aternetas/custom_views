package com.example.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customviews.databinding.ButtonsBinding

class BottomButtonsView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: ButtonsBinding

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.bottomButtonsStyle)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.buttons, this, true)
        binding = ButtonsBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.BottomButtonsView,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {
            val positiveButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtonText)
            positiveButton.text = positiveButtonText ?: "Ok"

            val positiveButtonColor = typedArray.getColor(
                    R.styleable.BottomButtonsView_bottomPositiveButtonBackgroundColor,
                    Color.GREEN
                )
            positiveButton.backgroundTintList = ColorStateList.valueOf(positiveButtonColor)

/*            val positiveButtonRippleColor = typedArray.getColor(
                R.styleable.BottomButtonsView_bottomPositiveButtonRippleColor,
                Color.RED
            )*/

            val negativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
            negativeButton.text = negativeButtonText ?: "No"

            val negativeButtonColor =
                typedArray.getColor(
                    R.styleable.BottomButtonsView_bottomNegativeButtonBackgroundColor,
                    Color.RED
                )
            negativeButton.backgroundTintList = ColorStateList.valueOf(negativeButtonColor)

            val isProgressMode =
                typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)
            if (isProgressMode) {
                positiveButton.visibility = GONE
                negativeButton.visibility = GONE
                progress.visibility = VISIBLE
            } else {
                positiveButton.visibility = VISIBLE
                negativeButton.visibility = VISIBLE
                progress.visibility = GONE
            }
        }

        typedArray.recycle()
    }
}