package net.cassiolandim.tegal

class PlanetTrackProgress(
    val ship: Ship,
    val planet: Planet,
) : ShipLocation {
    private var _progress: Int = 0
    val progress
        get() = _progress

    fun increment() {
        _progress++
        if (progress > planet.info.trackLength) {
            planet.moveTo(ship.player)
        }
    }
}