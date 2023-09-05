package net.cassiolandim.tegal

import java.util.*

data class Player(
    val name: String,
    val id: UUID = UUID.randomUUID(),
) : ShipLocation {

    private val _colonizedPlanets = mutableSetOf<Planet>()
    private var _empireTokens: Int = 1
    private var _energyTokens: Int = 2
    private var _cultureTokens: Int = 1

    val colonizedPlanets: Set<Planet>
        get() = _colonizedPlanets
    val empireTokens: Int
        get() = _empireTokens
    val energyTokens: Int
        get() = _energyTokens
    val cultureTokens: Int
        get() = _cultureTokens
    val galaxyPoints: Int
        get() = when (empireTokens) {
            2 -> 1
            3 -> 2
            4 -> 3
            5 -> 5
            6 -> 8
            else -> 0
        }
    val activeDice: Int
        get() = when (empireTokens) {
            2, 3 -> 5
            4, 5 -> 6
            6 -> 7
            else -> 4
        }

    val ships = mutableSetOf(
        Ship(this),
        Ship(this),
    )

    fun incrementEmpireTokens() {
        if (_empireTokens < 6) _empireTokens++
        if (_empireTokens == 3 || _empireTokens == 5) {
            addShip()
        }
    }

    fun colonizePlanet(planet: Planet) {
        _colonizedPlanets.add(planet)
    }
    private fun addShip() {
        ships.add(Ship(this))
    }
}
