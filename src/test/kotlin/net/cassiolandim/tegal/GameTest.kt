package net.cassiolandim.tegal

import kotlin.test.Test
import kotlin.test.assertEquals

class GameTest {

    @Test
    fun given_2_players_when_constructing() {
        val game = Game("Cássio", "Débora")
        assertEquals(2, game.players.size)
        assertEquals(4, game.planetsInGame.size)
    }

    @Test
    fun given_3_players_when_constructing() {
        val game = Game("Cássio", "Débora", "x")
        assertEquals(3, game.players.size)
        assertEquals(5, game.planetsInGame.size)
    }

    @Test
    fun given_4_players_when_constructing() {
        val game = Game("Cássio", "Débora", "x", "y")
        assertEquals(4, game.players.size)
        assertEquals(6, game.planetsInGame.size)
    }

    @Test
    fun given_5_players_when_constructing() {
        val game = Game("Cássio", "Débora", "x", "y", "z")
        assertEquals(5, game.players.size)
        assertEquals(6, game.planetsInGame.size)
    }

    @Test
    fun roll_dice() {
        val game = Game("Cássio", "Débora")
        game.rollDice()

        assertEquals(4, game.rolledDice.size)
    }

    @Test
    fun activate_move_ship_die() {
        val game = Game("Cássio", "Débora")
        val moveShipDie = rollAndFindMoveUpFace(game)

        game.activateMoveShipDie(moveShipDie.id)
    }

    private fun rollAndFindMoveUpFace(game: Game): Die {
        var die: Die? = null
        while (die == null) {
            game.rollDice()
            die = game.rolledDice.find { it.faceUp == DieFace.MOVE_SHIP }
        }
        return die
    }
}