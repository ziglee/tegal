package net.cassiolandim.tegal

class Ship(
    val player: Player,
    var currentLocation: ShipLocation
) {
    fun moveToPlayer() {
        if (currentLocation is Player) throw IllegalMoveException("Cannot move to same location")

        leaveOldLocation()

        TODO()
    }

    fun moveToPlanetSurface(planet: Planet) {
        if (planet == currentLocation) throw IllegalMoveException("Cannot move to same location")
        if (planet.surfaceShips.map { it.player }
                .contains(player)) throw IllegalMoveException("Planet surface already has this player on it")

        leaveOldLocation()

        planet.surfaceShips.add(this)
        currentLocation = planet
    }

    fun moveToPlanetOrbit(planet: Planet) {
        if (planet == currentLocation) throw IllegalMoveException("Cannot move to same location")
        if (planet.orbitsProgresses.map { it.ship.player }
                .contains(player)) throw IllegalMoveException("Planet orbit already has this player on it")

        leaveOldLocation()

        val newLocation = PlanetTrackProgress(this, planet)
        planet.orbitsProgresses.add(newLocation)
        currentLocation = newLocation
    }

    private fun leaveOldLocation() {
        when (val oldLocation = currentLocation) {
            is Planet -> oldLocation.surfaceShips.remove(this)
            is PlanetTrackProgress -> oldLocation.planet.orbitsProgresses.remove(oldLocation)
        }
    }
}
