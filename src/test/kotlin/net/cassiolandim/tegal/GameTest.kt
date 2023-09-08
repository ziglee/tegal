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
    fun game_started() {
        val game = Game("Cássio", "Débora")

        assertFalse(game.ended)
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
        var planet = game.planetsInGame.find { it.info.productionType == PlanetProductionType.ENERGY }
        if (planet == null) {
            val planetInfo = game.poolOfPlanets.find { it.productionType == PlanetProductionType.ENERGY }!!
            planet = Planet(game, planetInfo)
            val planets = setOf(planet)
            game.replacePlanetsInGame(planets)
        }
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
        var planet = game.planetsInGame.find { it.info.productionType == PlanetProductionType.CULTURE }
        if (planet == null) {
            val planetInfo = game.poolOfPlanets.find { it.productionType == PlanetProductionType.CULTURE }!!
            planet = Planet(game, planetInfo)
            val planets = setOf(planet)
            game.replacePlanetsInGame(planets)
        }
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
        var planet = game.planetsInGame.find { it.info.name == PlanetInfo.maia.name }
        if (planet == null) {
            val planetInfo = game.poolOfPlanets.find { it.name == PlanetInfo.maia.name }!!
            planet = Planet(game, planetInfo)
            val planets = setOf(planet)
            game.replacePlanetsInGame(planets)
        }
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
        var planet = game.planetsInGame.find { it.info.name == PlanetInfo.andellouxian6.name }
        if (planet == null) {
            val planetInfo = game.poolOfPlanets.find { it.name == PlanetInfo.andellouxian6.name }!!
            planet = Planet(game, planetInfo)
            val planets = setOf(planet)
            game.replacePlanetsInGame(planets)
        }
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
}