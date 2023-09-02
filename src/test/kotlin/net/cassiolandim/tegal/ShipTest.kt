package net.cassiolandim.tegal

import kotlin.test.Test
import kotlin.test.assertEquals

class ShipTest {

    private val player1 = Player("Cassio")
    private val player2 = Player("Debora")
    private val game = Game(
        players = setOf(player1, player2),
        planets = mutableSetOf(
            PlanetInfo.aughmoore.createPlanet(),
            PlanetInfo.drewkaiden.createPlanet(),
            PlanetInfo.gort.createPlanet(),
            PlanetInfo.maia.createPlanet(),
            PlanetInfo.nagato.createPlanet(),
        )
    )

    @Test(expected = IllegalMoveException::class)
    fun given_ship_is_in_player_when_moving_to_player_should_throw_exception() {
        val ship = player1.ships.first()
        ship.moveToPlayer()
    }

    @Test(expected = IllegalMoveException::class)
    fun given_a_ship_from_the_same_player_is_on_planet_surface_when_moving_another_ship_from_the_same_player_there_should_throw_exception() {
        val ship1 = player1.ships.elementAt(0)
        val ship2 = player1.ships.elementAt(1)
        val planet = PlanetInfo.gort.createPlanet()
        planet.surfaceShips.add(ship1)
        ship2.moveToPlanetSurface(planet)
    }

    @Test(expected = IllegalMoveException::class)
    fun given_a_ship_from_the_same_player_is_on_planet_orbit_when_moving_another_ship_from_the_same_player_there_should_throw_exception() {
        val ship1 = player1.ships.elementAt(0)
        val ship2 = player1.ships.elementAt(1)
        val planet = PlanetInfo.gort.createPlanet()
        planet.orbitsProgresses.add(
            PlanetTrackProgress(
                ship1, planet
            )
        )
        ship2.moveToPlanetOrbit(planet)
    }

    @Test
    fun given_no_ship_is_on_planet_surface_when_moving_ship_there_should() {
        val ship1 = player1.ships.elementAt(0)
        val planet = PlanetInfo.gort.createPlanet()
        ship1.moveToPlanetSurface(planet)
        planet.surfaceShips.contains(ship1)
        assertEquals(planet, ship1.currentLocation)
    }
}