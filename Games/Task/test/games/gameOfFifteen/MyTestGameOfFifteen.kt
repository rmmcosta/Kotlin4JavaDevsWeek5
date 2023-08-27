package games.gameOfFifteen

import board.Direction
import board.Direction.*
import games.game.Game
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MyTestGameOfFifteen {
    private fun Game.asString() =
        (1..4).joinToString("\n") { i ->
            (1..4).joinToString(" ") { j ->
                get(i, j)?.let { "%2d".format(it) } ?: " -"
            }
        }

    class TestGameInitializer(
        override val initialPermutation: List<Int>
    ) : GameOfFifteenInitializer

    private fun testGame(initialPermutation: List<Int>, moves: List<Move>): Game {
        val game = newGameOfFifteen(TestGameInitializer(initialPermutation))
        game.initialize()
        //println("game after initialize: ${game.asString()}")

        for ((index, move) in moves.withIndex()) {
            if (move.direction == null) continue
            // checking the state after each move
            Assert.assertTrue("The move for game of fifteen should be always possible", game.canMove())
            game.processMove(move.direction)
            val prev = moves[index - 1].board
            Assert.assertEquals(
                "Wrong result after pressing ${move.direction} " +
                        "for\n$prev\n",
                move.board, game.asString()
            )
        }
        return game
    }

    data class Move(
        val direction: Direction?,
        val initialBoard: String
    ) {
        val board: String = initialBoard.trimMargin()
    }

    @Test
    fun testMovesNoMove() {
        val game = testGame(
            listOf(3, 6, 13, 15, 7, 5, 8, 4, 14, 11, 12, 1, 10, 9, 2),
            listOf(
                Move(
                    null, """
            | 3  6 13 15
            | 7  5  8  4
            |14 11 12  1
            |10  9  2  -"""
                )
            )
        )
        val expected = """
            | 3  6 13 15
            | 7  5  8  4
            |14 11 12  1
            |10  9  2  -""".trimMargin()
        assertEquals(expected, game.asString())
    }

    @Test
    fun testMovesMoveRight() {
        val game = testGame(listOf(3, 6, 13, 15, 7, 5, 8, 4, 14, 11, 12, 1, 10, 9, 2),
            listOf(
                Move(
                    null, """
            | 3  6 13 15
            | 7  5  8  4
            |14 11 12  1
            |10  9  2  -"""
                ),
                Move(
                    RIGHT, """
            | 3  6 13 15
            | 7  5  8  4
            |14 11 12  1
            |10  9  -  2"""
                )
            ))
        val expected = """
            | 3  6 13 15
            | 7  5  8  4
            |14 11 12  1
            |10  9  -  2""".trimMargin()
        assertEquals(expected, game.asString())
    }
}