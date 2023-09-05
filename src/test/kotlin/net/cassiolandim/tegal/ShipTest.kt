package net.cassiolandim.tegal

import net.cassiolandim.tegal.exceptions.IllegalMoveException
import kotlin.test.*

class ShipTest {

    private lateinit var game: Game
    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var planet1: Planet
    private lateinit var planet2: Planet

    @BeforeTest
    fun before() {
        game = Game("Cássio", "Débora")
        player1 = game.players[0]
        player2 = game.players[1]
        planet1 = game.planetsInGame.elementAt(0)
        planet2 = game.planetsInGame.elementAt(1)
    }

    @Test(expected = IllegalMoveException::class)
    fun given_ship_is_in_player_when_moving_to_player_should_throw_exception() {
        val ship = player1.ships.first()
        ship.leaveOldLocationAndMoveToPlayer()
    }

    @Test(expected = IllegalMoveException::class)
    fun given_a_ship_from_the_same_player_is_on_planet_surface_when_moving_another_ship_from_the_same_player_there_should_throw_exception() {
        val ship1 = player1.ships.elementAt(0)
        val ship2 = player1.ships.elementAt(1)
        ship1.leaveOldLocationAndMoveToPlanetSurface(planet1)
        ship2.leaveOldLocationAndMoveToPlanetSurface(planet1)
    }

    @Test(expected = IllegalMoveException::class)
    fun given_a_ship_from_the_same_player_is_on_planet_orbit_when_moving_another_ship_from_the_same_player_there_should_throw_exception() {
        val ship1 = player1.ships.elementAt(0)
        val ship2 = player1.ships.elementAt(1)
        ship1.leaveOldLocationAndMoveToPlanetOrbit(planet1)
        ship2.leaveOldLocationAndMoveToPlanetOrbit(planet1)
    }

    @Test
    fun given_no_ship_is_on_planet_surface_when_moving_ship_there() {
        val ship = player1.ships.elementAt(0)
        ship.leaveOldLocationAndMoveToPlanetSurface(planet1)
        assertTrue(planet1.surfaceShips.contains(ship))
        assertEquals(planet1, ship.currentLocation)
    }

    @Test
    fun given_no_ship_is_on_planet_orbit_when_moving_ship_there() {
        val ship = player1.ships.elementAt(0)
        ship.leaveOldLocationAndMoveToPlanetOrbit(planet1)
        assertTrue(planet1.orbitsProgresses.map { it.ship }.contains(ship))
    }

    @Test
    fun given_ship_is_on_planet_orbit_when_incrementing_progress() {
        val ship = player1.ships.elementAt(0)
        val progressTrack = ship.leaveOldLocationAndMoveToPlanetOrbit(planet1)
        assertEquals(0, progressTrack.progress)

        ship.incrementProgressOnOrbit()
        assertEquals(1, progressTrack.progress)
    }

    @Test
    fun given_ship_is_on_planet_orbit_when_it_reaches_track_end() {
        val ship1Player1 = player1.ships.elementAt(0)
        val ship2Player1 = player1.ships.elementAt(1)
        val ship1Player2 = player2.ships.elementAt(0)
        val ship2Player2 = player2.ships.elementAt(1)
        ship1Player1.leaveOldLocationAndMoveToPlanetOrbit(planet1)
        ship2Player1.leaveOldLocationAndMoveToPlanetSurface(planet1)
        ship1Player2.leaveOldLocationAndMoveToPlanetOrbit(planet1)
        ship2Player2.leaveOldLocationAndMoveToPlanetSurface(planet1)

        (1..(planet1.info.trackLength)).forEach {
            ship1Player1.incrementProgressOnOrbit()
        }

        assertEquals(player1, ship1Player1.currentLocation)
        assertEquals(player1, ship2Player1.currentLocation)
        assertEquals(player2, ship1Player2.currentLocation)
        assertEquals(player2, ship2Player2.currentLocation)
        assertTrue(player1.colonizedPlanets.contains(planet1))
        assertFalse(game.planetsInGame.contains(planet1))
    }

    @Test
    fun given_ship_is_on_planet_surface_when_moving_ship_to_player() {
        val ship = player1.ships.elementAt(0)
        ship.leaveOldLocationAndMoveToPlanetSurface(planet1)

        ship.leaveOldLocationAndMoveToPlayer()
        assertEquals(player1, ship.currentLocation)
    }
}