package com.example.customviews.tictactoe

import android.content.Context
import android.graphics.Color
import android.graphics.RectF
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
            updateViewSize()
            requestLayout()
            invalidate()
        }
    private var playerOneColor by Delegates.notNull<Int>()
    private var playerTwoColor by Delegates.notNull<Int>()
    private var gridColor by Delegates.notNull<Int>()

    private val fieldRect = RectF(0f, 0f, 0f, 0f)
    private var cellSize: Float = 0f
    private var cellPadding: Float = 0f

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
        updateViewSize()
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
        playerOneColor = PLAYER_X_COLOR
        playerTwoColor = PLAYER_O_COLOR
        gridColor = GRID_COLOR
    }
    
    private fun updateViewSize() {
        val field = this.field ?: return

        val safeWidth = width - paddingStart - paddingEnd
        val safeHeight = height - paddingTop - paddingBottom

        val cellWidth = safeWidth / field.columns.toFloat()
        val cellHeight = safeHeight / field.rows.toFloat()

        val paddingValue = 0.2f
        cellSize = cellWidth.coerceAtMost(cellHeight)
        cellPadding = cellSize * paddingValue

        val fieldWidth = cellSize * field.columns
        val fieldHeight = cellSize * field.rows

        fieldRect.set(
            paddingStart + (safeWidth - fieldWidth) / 2,
            paddingTop + (safeHeight - fieldHeight) / 2,
            fieldRect.left + fieldWidth,
            fieldRect.top + fieldHeight
        )
    }
}