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
            : this(context, attrs, defStyleAttr, R.style.myTicTacToeStyle)

    constructor(context: Context, attrs: AttributeSet?)
            : this(context, attrs, R.attr.ticTacToeStyle)

    constructor(context: Context) : this(context, null)
}