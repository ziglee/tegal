package net.cassiolandim.tegal

import net.cassiolandim.tegal.exceptions.IllegalMoveException
import java.util.*

data class Player(
    val name: String,
    val id: UUID = UUID.randomUUID(),
) : ShipLocation {

    private val _colonizedPlanets = mutableSetOf<Planet>()
    private var _empireLevel: Int = 1
    private var _energyLevel: Int = 2
    private var _cultureLevel: Int = 1

    val ships = mutableSetOf(
        Ship(this),
        Ship(this),
    )

    val colonizedPlanets: Set<Planet>
        get() = _colonizedPlanets
    val empireLevel: Int
        get() = _empireLevel
    val energyLevel: Int
        get() = _energyLevel
    val cultureLevel: Int
        get() = _cultureLevel
    val galaxyPoints: Int
        get() = when (empireLevel) {
            2 -> 1
            3 -> 2
            4 -> 3
            5 -> 5
            6 -> 8
            else -> 0
        }
    val activeDice: Int
        get() = when (empireLevel) {
            2, 3 -> 5
            4, 5 -> 6
            6 -> 7
            else -> 4
        }
    val totalPoints: Int
        get() = colonizedPlanets.sumOf { it.info.pointsWorth } + galaxyPoints

    fun incrementEmpireTokens() {
        if (_empireLevel < 6) _empireLevel++
        if (_empireLevel == 3 || _empireLevel == 5) {
            addShip()
        }
    }

    fun colonizePlanet(planet: Planet) {
        _colonizedPlanets.add(planet)
    }

    private fun addShip() {
        ships.add(Ship(this))
    }

    fun spendEnergyTokens(amount: Int) {
        if (energyLevel < amount)  throw IllegalMoveException("Player has no sufficient energy tokens to spend")
        _energyLevel -= amount
    }

    fun spendCultureTokens(amount: Int) {
        if (cultureLevel < amount)  throw IllegalMoveException("Player has no sufficient culture tokens to spend")
        _cultureLevel -= amount
    }
}
