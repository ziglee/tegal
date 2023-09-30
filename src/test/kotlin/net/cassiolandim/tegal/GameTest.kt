package net.cassiolandim.tegal

import kotlin.test.*

class GameTest {

    companion object {
        /**
         * If the planet is not in game, it will be inserted.
         */
        private fun fetchPlanetFromGame(planetInfo: PlanetInfo, game: Game): Planet {
            var planet = game.planetsInGame.find { it.info.name == planetInfo.name }
            if (planet == null) {
                planet = Planet(game, planetInfo)
                game.replacePlanetsInGame(setOf(planet))
            }
            return planet
        }
    }

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
    fun game_started() {
        val game = Game("Cássio", "Débora")

        assertFalse(game.ended)
        assertNull(game.playerWhoTriggeredEndGame)
        assertEquals(1, game.roundCount)
        assertEquals(4, game.rolledDice.size)
        assertEquals("Cássio", game.currentPlayer.name)
    }

    @Test
    fun activate_move_ship_to_surface_die() {
        val game = Game("Cássio", "Débora")
        game.fakeRollDiceAll(DieFace.MOVE_SHIP)
        val moveShipDie = game.rolledDice.first()
        val ship = game.currentPlayer.ships.elementAt(0)
        val planet = game.planetsInGame.elementAt(0)

        game.activateDieMoveShipToPlanetSurface(
            dieId = moveShipDie.id,
            shipId = ship.id,
            planetId = planet.id
        )

        assertEquals(listOf(moveShipDie), game.activationBay)
        assertEquals(moveShipDie, game.lastActivatedDie)
        assertEquals(3, game.rolledDice.size)
        assertFalse(game.rolledDice.contains(moveShipDie))
    }

    @Test
    fun activate_move_ship_to_orbit_die() {
        val game = Game("Cássio", "Débora")
        game.fakeRollDiceAll(DieFace.MOVE_SHIP)
        val moveShipDie = game.rolledDice.first()
        val ship = game.currentPlayer.ships.elementAt(0)
        val planet = game.planetsInGame.elementAt(0)

        game.activateDieMoveShipToPlanetOrbit(
            dieId = moveShipDie.id,
            shipId = ship.id,
            planetId = planet.id
        )

        assertEquals(listOf(moveShipDie), game.activationBay)
        assertEquals(moveShipDie, game.lastActivatedDie)
        assertEquals(3, game.rolledDice.size)
        assertFalse(game.rolledDice.contains(moveShipDie))
    }

    @Test
    fun free_reroll_dice() {
        val game = Game("Cássio", "Débora")
        game.fakeRollDiceAll(DieFace.MOVE_SHIP)

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
        assertNotEquals(rolledDice, game.rolledDice)
        assertTrue(game.hasUsedFreeReroll)
    }

    @Test
    fun paid_reroll_dice() {
        val game = Game("Cássio", "Débora")
        game.fakeRollDiceAll(DieFace.MOVE_SHIP)

        val rolledDice = game.rolledDice
        val diceToRerollIds = setOf(rolledDice[0].id, rolledDice[1].id)
        game.paidReroll(diceToRerollIds)

        assertEquals(4, game.rolledDice.size)
        assertFalse(game.rolledDice.contains(rolledDice[0]))
        assertFalse(game.rolledDice.contains(rolledDice[1]))
        assertTrue(game.rolledDice.contains(rolledDice[2]))
        assertTrue(game.rolledDice.contains(rolledDice[3]))
    }

    @Test
    fun activate_acquire_energy_die() {
        val game = Game("Cássio", "Débora")
        val player = game.currentPlayer
        val planet = fetchPlanetFromGame(PlanetInfo.aughmoore, game)
        player.ships.add(Ship(player))
        player.ships.elementAt(0).leaveOldLocationAndMoveToPlanetSurface(planet)
        player.ships.elementAt(1).leaveOldLocationAndMoveToPlanetOrbit(planet)
        game.fakeRollDiceAll(DieFace.ACQUIRE_ENERGY)
        val die = game.rolledDice.first()

        assertEquals(2, player.energyLevel)

        game.activateDieAcquireEnergy(die.id)

        assertEquals(5, player.energyLevel)
    }

    @Test
    fun activate_acquire_culture_die() {
        val game = Game("Cássio", "Débora")
        val player = game.currentPlayer
        val planet = fetchPlanetFromGame(PlanetInfo.andellouxian6, game)
        player.ships.elementAt(0).leaveOldLocationAndMoveToPlanetSurface(planet)
        player.ships.elementAt(1).leaveOldLocationAndMoveToPlanetOrbit(planet)
        game.fakeRollDiceAll(DieFace.ACQUIRE_CULTURE)
        val die = game.rolledDice.first()

        assertEquals(1, player.cultureLevel)

        game.activateDieAcquireCulture(die.id)

        assertEquals(3, player.cultureLevel)
    }

    @Test
    fun activate_advance_diplomacy_die() {
        val game = Game("Cássio", "Débora")
        val player = game.currentPlayer
        val ship = player.ships.elementAt(0)
        val planet = fetchPlanetFromGame(PlanetInfo.maia, game)
        ship.leaveOldLocationAndMoveToPlanetOrbit(planet)
        val location = ship.currentLocation as PlanetTrackProgress
        game.fakeRollDiceAll(DieFace.ADVANCE_DIPLOMACY)
        val die = game.rolledDice.first()

        assertEquals(0, location.progress)

        game.activateDieAdvanceDiplomacy(die.id, ship.id)

        assertEquals(1, location.progress)
    }

    @Test
    fun activate_advance_economy_die() {
        val game = Game("Cássio", "Débora")
        val player = game.currentPlayer
        val ship = player.ships.elementAt(0)
        val planet = fetchPlanetFromGame(PlanetInfo.andellouxian6, game)
        ship.leaveOldLocationAndMoveToPlanetOrbit(planet)
        val location = ship.currentLocation as PlanetTrackProgress
        game.fakeRollDiceAll(DieFace.ADVANCE_ECONOMY)
        val die = game.rolledDice.first()

        assertEquals(0, location.progress)

        game.activateDieAdvanceEconomy(die.id, ship.id)

        assertEquals(1, location.progress)
    }

    @Test
    fun activate_upgrade_empire_die() {
        val game = Game("Cássio", "Débora")
        val player = game.currentPlayer
        game.fakeRollDiceAll(DieFace.UTILIZE_COLONY)
        val die = game.rolledDice.first()
        val oldEmpireLevel = player.empireLevel

        assertEquals(4, player.diceCount)

        game.activateDieUpgradeEmpire(die.id, PlanetProductionType.ENERGY)
        assertEquals(4, player.diceCount)
        assertEquals(oldEmpireLevel + 1, player.empireLevel)

        game.endTurn()
        assertEquals(5, player.diceCount)
    }

    @Test
    fun activate_utilize_colony_andellouxian6_die() {
        val game = Game("Cássio", "Débora")
        val player = game.currentPlayer
        val ship = player.ships.elementAt(0)
        val planet = fetchPlanetFromGame(PlanetInfo.andellouxian6, game)
        ship.leaveOldLocationAndMoveToPlanetOrbit(planet)
        val location = ship.currentLocation as PlanetTrackProgress
        game.fakeRollDiceAll(DieFace.UTILIZE_COLONY)
        val die = game.rolledDice.first()

        location.increment()
        location.increment()
        location.increment()

        game.activateDieUtilizeColonyAndellouxian6(
            dieId = die.id,
            planetId = planet.id,
            shipToMoveId = ship.id,
            energyToAcquire = 2,
            cultureToAcquire = 1
        )

        assertEquals(4, player.energyLevel)
        assertEquals(2, player.cultureLevel)
    }

    @Test
    fun deliberately_end_turn() {
        val game = Game("Cássio", "Débora")
        val rolledDice = game.rolledDice

        game.endTurn()
        assertNotEquals(rolledDice, game.rolledDice)
        assertEquals("Débora", game.currentPlayer.name)
        assertEquals(1, game.roundCount)

        game.endTurn()
        assertEquals("Cássio", game.currentPlayer.name)
        assertEquals(2, game.roundCount)
    }

    @Test
    fun convert_dice() {
        val game = Game("Cássio", "Débora")
        game.fakeRollDiceAll(DieFace.MOVE_SHIP)
        val rolledDice = game.rolledDice
        val dieToConvertId = rolledDice[2].id

        val dicePair = Pair(rolledDice[0].id, rolledDice[1].id)
        game.convertDie(dicePair, dieToConvertId, DieFace.ACQUIRE_ENERGY)

        assertEquals(2, game.rolledDice.size)
        assertEquals(DieFace.ACQUIRE_ENERGY, game.rolledDice.findById(dieToConvertId).faceUp)
    }

    @Test
    fun check_end_of_game_trigger() {
        val game = Game("Cássio", "Débora")
        val firstPlayer = game.players[0]
        val lastPlayer = game.players[1]
        val p1 = Planet(game, PlanetInfo.andellouxian6)
        val p2 = Planet(game, PlanetInfo.aughmoore)
        val p3 = Planet(game, PlanetInfo.brumbaugh)
        val p4 = Planet(game, PlanetInfo.bsw101)
        val p5 = Planet(game, PlanetInfo.clj0517)
        game.replacePlanetsInGame(setOf(p1, p2, p3, p4, p5))
        firstPlayer.colonizePlanet(p1)
        firstPlayer.colonizePlanet(p2)
        firstPlayer.colonizePlanet(p3)
        firstPlayer.colonizePlanet(p4)
        firstPlayer.colonizePlanet(p5)

        game.fakeRollDiceAll(DieFace.ACQUIRE_ENERGY)
        val die = game.rolledDice.first()
        game.activateDieAcquireEnergy(die.id) // just to trigger the checkEndOfGame

        assertEquals(firstPlayer, game.playerWhoTriggeredEndGame)
        assertFalse(game.ended)

        game.endTurn()
        assertFalse(game.ended)

        game.endTurn()
        assertEquals(lastPlayer, game.currentPlayer)
        assertTrue(game.ended)
    }
}