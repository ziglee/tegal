package net.cassiolandim.tegal

import java.util.*

open class Planet(
    val game: Game,
    val info: PlanetInfo,
    val id: UUID = UUID.randomUUID(),
) : ShipLocation {

    private val _surfaceShips: MutableSet<Ship> = mutableSetOf()
    private val _orbitsProgresses: MutableSet<PlanetTrackProgress> = mutableSetOf()

    val surfaceShips: Set<Ship>
        get() = _surfaceShips
    val orbitsProgresses: Set<PlanetTrackProgress>
        get() = _orbitsProgresses

    fun moveTo(player: Player) {
        surfaceShips.forEach { it.resetLocation() }
        orbitsProgresses.forEach { it.ship.resetLocation() }
        _surfaceShips.clear()
        _orbitsProgresses.clear()
        player.colonizePlanet(this)
        game.removePlanetFromGame(this)
    }

    fun addToSurface(ship: Ship) {
        _surfaceShips.add(ship)
    }

    fun addToOrbit(ship: Ship): PlanetTrackProgress {
        val progress = PlanetTrackProgress(ship, this)
        _orbitsProgresses.add(progress)
        return progress
    }

    fun removeFromSurface(ship: Ship) {
        _surfaceShips.remove(ship)
    }

    fun removeFromOrbit(progress: PlanetTrackProgress) {
        _orbitsProgresses.remove(progress)
    }
}