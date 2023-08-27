package games.game2048

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game = Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) {
    val newValue = initializer.nextValue(this)
    if (newValue != null) {
        this[newValue.first] = newValue.second
    }
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {/*val gameBoardCells: List<Int?> = rowOrColumn.map { cell: Cell -> this[cell] }
    val listWithMerge = gameBoardCells.moveAndMergeEqual { it * 2 }
    for ((index, cell) in rowOrColumn.withIndex()) {
        if (index <= listWithMerge.lastIndex) {
            this[cell] = listWithMerge[index]
        } else {
            this[cell] = null
        }
    }
    return listWithMerge.size != rowOrColumn.size*/
    val gameBoardCells: List<Int?> = rowOrColumn.map { cell: Cell -> this[cell] }
    //println("game board cells: $gameBoardCells")
    if (gameBoardCells.all { it == null }) {
        return false
    }
    val listWithMerge = gameBoardCells.moveAndMergeEqual { it * 2 }
    //println("list with merge: $listWithMerge")
    val newValues = listWithMerge + List(rowOrColumn.size - listWithMerge.size) { null }
    //println("list with new values: $newValues")
    rowOrColumn.zip(newValues).forEach { (cell, value) ->
        this[cell] = value
    }
    //println("movedValues: ${listWithMerge.size != rowOrColumn.size}")
    return listWithMerge.size != rowOrColumn.size
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
/*fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    //moving left or right - moves rows
    //moving up or down - moves columns
    //moving right or down - implies reversing the list before and after moving
    return when (direction) {
        Direction.UP -> moveAllColumns(this)
        Direction.DOWN -> moveAllColumnsReverse(this)
        Direction.LEFT -> moveAllRows(this)
        Direction.RIGHT -> moveAllRowsReverse(this)
    }
}

private fun moveAllRows(gameBoard: GameBoard<Int?>): Boolean {
    var row = 1
    var hasMoved = false
    while (row <= gameBoard.width) {
        hasMoved = gameBoard.moveValuesInRowOrColumn(gameBoard.getRow(row, 1..gameBoard.width)) || hasMoved
        row++
    }
    return hasMoved
}

private fun moveAllRowsReverse(gameBoard: GameBoard<Int?>): Boolean {
    var row = 1
    var hasMoved = false
    while (row <= gameBoard.width) {
        //println(gameBoard.getRow(row, 1..gameBoard.width).reversed())
        hasMoved = gameBoard.moveValuesInRowOrColumn(gameBoard.getRow(row, 1..gameBoard.width).reversed()) || hasMoved
        row++
    }
    return hasMoved
}

private fun moveAllColumns(gameBoard: GameBoard<Int?>): Boolean {
    var column = 1
    var hasMoved = false
    while (column <= gameBoard.width) {
        hasMoved = gameBoard.moveValuesInRowOrColumn(gameBoard.getColumn(1..gameBoard.width, column)) || hasMoved
        column++
    }
    return hasMoved
}

private fun moveAllColumnsReverse(gameBoard: GameBoard<Int?>): Boolean {
    var column = 1
    var hasMoved = false
    while (column <= gameBoard.width) {
        hasMoved =
            gameBoard.moveValuesInRowOrColumn(gameBoard.getColumn(1..gameBoard.width, column).reversed()) || hasMoved
        column++
    }
    return hasMoved
}*/
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    return when (direction) {
        Direction.UP -> moveAllColumns()
        Direction.DOWN -> moveAllColumnsReverse()
        Direction.LEFT -> moveAllRows()
        Direction.RIGHT -> moveAllRowsReverse()
    }
}

private fun GameBoard<Int?>.moveAllRows(): Boolean {
    return (1..width).map { rowNum: Int -> moveValuesInRowOrColumn(getRow(rowNum, 1..width)) }
        .any { hasMoved: Boolean -> hasMoved }
}

private fun GameBoard<Int?>.moveAllRowsReverse(): Boolean {
    return (1..width).map { rowNum: Int -> moveValuesInRowOrColumn(getRow(rowNum, 1..width).reversed()) }
        .any { hasMoved: Boolean -> hasMoved }
}

private fun GameBoard<Int?>.moveAllColumns(): Boolean {
    return (1..width).map { rowNum: Int -> moveValuesInRowOrColumn(getColumn(1..width, rowNum)) }
        .any { hasMoved: Boolean -> hasMoved }
}

private fun GameBoard<Int?>.moveAllColumnsReverse(): Boolean {
    return (1..width).map { rowNum: Int -> moveValuesInRowOrColumn(getColumn(1..width, rowNum).reversed()) }
        .any { hasMoved: Boolean -> hasMoved }
}
