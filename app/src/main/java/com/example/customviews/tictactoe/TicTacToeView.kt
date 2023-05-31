package com.example.customviews.tictactoe

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
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
        set(value) {
            field?.listeners?.remove(listener)
            field = value
            field?.listeners?.add(listener)
            requestLayout()
            invalidate()
        }
    private var playerOneColor by Delegates.notNull<Int>()
    private var playerTwoColor by Delegates.notNull<Int>()
    private var gridColor by Delegates.notNull<Int>()

    private var listener: OnFieldChangedListener = {

    }

    companion object {
        const val PLAYER_X_COLOR = Color.RED
        const val PLAYER_O_COLOR = Color.BLUE
        const val GRID_COLOR = Color.BLACK

        const val DESIRED_CELL_SIZE = 30F
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        field?.listeners?.add(listener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        field?.listeners?.remove(listener)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = suggestedMinimumWidth + paddingStart + paddingEnd
        val minHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        val desiredCellSizeInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            DESIRED_CELL_SIZE,
            resources.displayMetrics
        ).toInt()
        val rows = field?.rows ?: 0
        val columns = field?.columns ?: 0

        val desiredWidth =
            minWidth.coerceAtLeast(columns * desiredCellSizeInPixels + paddingStart + paddingEnd)
        val desiredHeight =
            minHeight.coerceAtLeast(rows * desiredCellSizeInPixels + paddingTop + paddingBottom)

        setMeasuredDimension(
            resolveSize(desiredWidth, widthMeasureSpec),
            resolveSize(desiredHeight, heightMeasureSpec)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        TODO("Not yet implemented")
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