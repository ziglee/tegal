package net.cassiolandim.tegal

class PlanetTrackProgress(
    val ship: Ship,
    val planet: Planet,
    var progress: Int = 0,
) : ShipLocation