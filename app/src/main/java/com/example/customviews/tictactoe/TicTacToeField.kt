package com.example.customviews.tictactoe

enum class CELL_STATE {
    EMPTY, X, Y
}
//or field: TicTacToeField
typealias onCellStateChangedListener = (row: Int, column: Int) -> Unit

class TicTacToeField(
    // to do: input validation
    val _rows: Int,
    val _columns: Int
) {
    companion object {
        const val MIN_VALUE = 3
        const val MAX_VALUE = 9
    }

    //numeration starts from zero for rows&columns?
    private var rows = _rows
        set(value) {
            if (value in MIN_VALUE..MAX_VALUE)
                field = value
        }
    private var columns = _columns
        set(value) {
            if (value in MIN_VALUE..MAX_VALUE)
                field = value
        }
    private val cells = Array(rows) { Array(columns) { CELL_STATE.EMPTY } }

    val listeners = mutableSetOf<onCellStateChangedListener>()

    private fun getCell(row: Int, column: Int): CELL_STATE {
        // is it necessary?
        //return IndexOutOfBoundsException
        if ((row !in 1..rows) && (column !in 1..columns)) return CELL_STATE.EMPTY
        return cells[row][column]
    }

    private fun setCell(row: Int, column: Int, state: CELL_STATE) {
        var cell = getCell(row, column)
        if (cell != state) {
            cell = state
            listeners.forEach { it.invoke(row, column) }
        }
    }
}