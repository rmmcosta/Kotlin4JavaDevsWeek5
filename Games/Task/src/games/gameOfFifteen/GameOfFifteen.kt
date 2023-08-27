package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game
import java.lang.IllegalArgumentException
import kotlin.random.Random

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = Game15(initializer)

class Game15(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)
    override fun initialize() {
        for ((index, value) in initializer.initialPermutation.withIndex()) {
            val cell = board.getCell(index / 4 + 1, index % 4 + 1)
            board[cell] = value
        }
        /*val randomEmptyPosition = Random.nextInt(0, 15)
        var index = 0
        var initializerIndex = 0
        while (initializerIndex < initializer.initialPermutation.lastIndex) {
            val cell = board.getCell(index / 4 + 1, index % 4 + 1)
            if (index != randomEmptyPosition) {
                board[cell] = initializer.initialPermutation[initializerIndex]
                initializerIndex++
            } else {
                board[cell] = null
            }
            index++
        }*/
    }

    override fun canMove(): Boolean {
        return true

    }

    override fun hasWon(): Boolean {
        for ((index, cell) in board.getAllCells().withIndex()) {
            if ((board[cell] == null && index != 15) || (board[cell] != null && board[cell] != index + 1)) {
                return false
            }
        }
        return true
    }

    override fun processMove(direction: Direction) {
        val emptyCell = getEmptyCell()
        when (direction) {
            Direction.LEFT -> {
                if (emptyCell.canMoveLeft()) {
                    val leftCell = board.getCell(emptyCell.i, emptyCell.j + 1)
                    swapCells(emptyCell, leftCell)
                }
            }

            Direction.RIGHT -> {
                if (emptyCell.canMoveRight()) {
                    val rightCell = board.getCell(emptyCell.i, emptyCell.j - 1)
                    swapCells(emptyCell, rightCell)
                }
            }

            Direction.UP -> {
                if (emptyCell.canMoveUp()) {
                    val upCell = board.getCell(emptyCell.i + 1, emptyCell.j)
                    swapCells(emptyCell, upCell)
                }
            }

            Direction.DOWN -> {
                if (emptyCell.canMoveDown()) {
                    val downCell = board.getCell(emptyCell.i - 1, emptyCell.j)
                    swapCells(emptyCell, downCell)
                }
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board[board.getCell(i, j)]

    private fun swapCells(cell1: Cell, cell2: Cell) {
        val cellValue = board[cell1]
        board[cell1] = board[cell2]
        board[cell2] = cellValue
    }

    private fun Cell.canMoveLeft() = this.j < board.width
    private fun Cell.canMoveRight() = this.j > 1
    private fun Cell.canMoveUp() = this.i < board.width
    private fun Cell.canMoveDown() = this.i > 1

    private fun getEmptyCell() = board.getAllCells().first { board[it] == null }

}

