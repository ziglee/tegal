package net.cassiolandim.tegal

import net.cassiolandim.tegal.exceptions.IllegalMoveException
import java.util.*

class Ship(
    val player: Player,
    val id: UUID = UUID.randomUUID(),
) {
    private var _currentLocation: ShipLocation = player
    val currentLocation: ShipLocation
        get() = _currentLocation

    fun resetLocation() {
        _currentLocation = player
    }
    
    fun leaveOldLocationAndMoveToPlayer() {
        if (currentLocation is Player) throw IllegalMoveException("Cannot move to same location")

        leaveOldLocation()

        _currentLocation = player
    }

    fun leaveOldLocationAndMoveToPlanetSurface(planet: Planet) {
        if (planet == currentLocation) throw IllegalMoveException("Cannot move to same location")
        if (planet.surfaceShips.map { it.player }
                .contains(player)) throw IllegalMoveException("Planet surface already has this player on it")

        leaveOldLocation()

        planet.addToSurface(this)
        _currentLocation = planet
    }

    fun leaveOldLocationAndMoveToPlanetOrbit(planet: Planet): PlanetTrackProgress {
        if (planet == currentLocation) throw IllegalMoveException("Cannot move to same location")
        if (planet.orbitsProgresses.map { it.ship.player }
                .contains(player)) throw IllegalMoveException("Planet orbit already has this player on it")

        leaveOldLocation()

        val newLocation = planet.addToOrbit(this)
        _currentLocation = newLocation

        return newLocation
    }

    fun incrementProgressOnOrbit() {
        val location = currentLocation
        if (location is PlanetTrackProgress) {
            location.increment()
        } else throw IllegalMoveException("Ship is not on an orbit")
    }

    private fun leaveOldLocation() {
        when (val oldLocation = currentLocation) {
            is Planet -> oldLocation.removeFromSurface(this)
            is PlanetTrackProgress -> oldLocation.planet.removeFromOrbit(oldLocation)
        }
    }
}
