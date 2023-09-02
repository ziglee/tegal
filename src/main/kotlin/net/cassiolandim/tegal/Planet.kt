package net.cassiolandim.tegal

open class Planet(
    val info: PlanetInfo,
    val surfaceShips: MutableSet<Ship> = mutableSetOf(),
    val orbitsProgresses: MutableSet<PlanetTrackProgress> = mutableSetOf(),
) : ShipLocation