package com.example.customviews.tictactoe

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.example.customviews.R
import kotlin.properties.Delegates

class TicTacToeView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : View(context, attrs, defStyleAttr, defStyleRes) {
    var field: TicTacToeField? = null
    private var playerOneColor by Delegates.notNull<Int>()
    private var playerTwoColor by Delegates.notNull<Int>()
    private var gridColor by Delegates.notNull<Int>()

    companion object {
        const val PLAYER_X_COLOR = Color.RED
        const val PLAYER_O_COLOR = Color.BLUE
        const val GRID_COLOR = Color.BLACK
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, R.style.defaultTicTacToeFieldStyle)

    constructor(context: Context, attrs: AttributeSet?)
            : this(context, attrs, R.attr.ticTacToeFieldStyle)

    constructor(context: Context) : this(context, null)

    init {
        if (attrs != null) {
            initializeAttributes(attrs, defStyleAttr, defStyleRes)
        } else {
            initializeDefaultTheme()
        }
    }

    private fun initializeAttributes(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context
            .obtainStyledAttributes(attrs, R.styleable.TicTacToeView, defStyleAttr, defStyleRes)

        playerOneColor =
            typedArray.getColor(R.styleable.TicTacToeView_playerOneColor, PLAYER_X_COLOR)
        playerTwoColor =
            typedArray.getColor(R.styleable.TicTacToeView_playerTwoColor, PLAYER_O_COLOR)
        gridColor = typedArray.getColor(R.styleable.TicTacToeView_gridColor, GRID_COLOR)

        typedArray.recycle()
    }

    private fun initializeDefaultTheme() {
        playerOneColor = PLAYER_O_COLOR
        playerTwoColor = PLAYER_X_COLOR
        gridColor = GRID_COLOR
    }
}