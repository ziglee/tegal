package net.cassiolandim.tegal

import kotlin.test.*

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
        game.fakeRollDiceAllMoveUp()
        val moveShipDie = game.rolledDice.first()
        val ship = game.currentPlayer.ships.elementAt(0)
        val planet = game.planetsInGame.elementAt(0)

        game.activateDieMoveShipToPlanetSurface(
            dieId = moveShipDie.id,
            shipId = ship.id,
            planetId = planet.id
        )

        assertEquals(listOf(moveShipDie), game.activationBay)
        assertEquals(moveShipDie, game.activateDie)
        assertEquals(3, game.rolledDice.size)
        assertFalse(game.rolledDice.contains(moveShipDie))
    }

    @Test
    fun free_reroll_dice() {
        val game = Game("Cássio", "Débora")
        game.fakeRollDiceAllMoveUp()

        val moveShipDie = game.rolledDice.first()
        val ship = game.currentPlayer.ships.elementAt(0)
        val planet = game.planetsInGame.elementAt(0)

        game.activateDieMoveShipToPlanetSurface(
            dieId = moveShipDie.id,
            shipId = ship.id,
            planetId = planet.id
        )
        val rolledDice = game.rolledDice
        game.freeRerollDice()

        assertEquals(listOf(moveShipDie), game.activationBay)
        assertTrue(game.hasUsedFreeReroll)
        assertNotEquals(rolledDice, game.rolledDice)
    }

    @Test
    fun paid_reroll_dice() {
        val game = Game("Cássio", "Débora")
        game.fakeRollDiceAllMoveUp()

        val rolledDice = game.rolledDice
        val diceToRerollIds = setOf(rolledDice[0].id, rolledDice[1].id)
        game.paidReroll(diceToRerollIds)

        assertEquals(4, game.rolledDice.size)
        assertFalse(game.rolledDice.contains(rolledDice[0]))
        assertFalse(game.rolledDice.contains(rolledDice[1]))
        assertTrue(game.rolledDice.contains(rolledDice[2]))
        assertTrue(game.rolledDice.contains(rolledDice[3]))
    }
}