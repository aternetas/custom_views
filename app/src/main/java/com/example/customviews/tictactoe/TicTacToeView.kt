package com.example.customviews.tictactoe

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.example.customviews.R

class TicTacToeView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : View(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, R.style.defaultTicTacToeFieldStyle)
    constructor(context: Context, attrs: AttributeSet?)
            : this(context, attrs, R.attr.ticTacToeFieldStyle)
    constructor(context: Context) : this(context, null)

    init {
        if (attrs != null) {
            initializeAttributes(attrs, defStyleAttr, defStyleRes)
        }
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
    }
}