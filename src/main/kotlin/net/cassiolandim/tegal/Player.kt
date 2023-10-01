package net.cassiolandim.tegal

import net.cassiolandim.tegal.exceptions.IllegalMoveException
import net.cassiolandim.tegal.planet.Planet
import net.cassiolandim.tegal.planet.PlanetProductionType
import net.cassiolandim.tegal.ship.Ship
import net.cassiolandim.tegal.ship.ShipLocation
import java.util.*

data class Player(
    val name: String,
    val id: UUID = UUID.randomUUID(),
) : ShipLocation {

    companion object {
        private const val MAX_ENERGY_CULTURE_LEVEL = 7
    }

    private val _colonizedPlanets = mutableSetOf<Planet>()
    private var _empireLevel: Int = 1
    private var _energyLevel: Int = 2
    private var _cultureLevel: Int = 1
    private var _diceCount: Int = 4

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
    val diceCount: Int
        get() = _diceCount
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

    fun addDice(amount: Int) {
        _diceCount += amount
    }

    fun spendEnergy(amount: Int) {
        if (energyLevel < amount)  throw IllegalMoveException("Player has no sufficient energy tokens to spend")
        _energyLevel -= amount
    }

    fun spendCulture(amount: Int) {
        if (cultureLevel < amount)  throw IllegalMoveException("Player has no sufficient culture tokens to spend")
        _cultureLevel -= amount
    }

    fun incrementEnergy(amount: Int) {
        _energyLevel += amount
        if (_energyLevel > MAX_ENERGY_CULTURE_LEVEL) _energyLevel = MAX_ENERGY_CULTURE_LEVEL
    }

    fun incrementCulture(amount: Int) {
        _cultureLevel += amount
        if (_cultureLevel > MAX_ENERGY_CULTURE_LEVEL) _cultureLevel = MAX_ENERGY_CULTURE_LEVEL
    }

    fun upgradeEmpire(resourceType: PlanetProductionType) {
        val nextLevelRequiredAmount = empireLevel + 1
        when (resourceType) {
            PlanetProductionType.ENERGY -> spendEnergy(nextLevelRequiredAmount)
            PlanetProductionType.CULTURE -> spendCulture(nextLevelRequiredAmount)
        }
        _empireLevel++
    }
}
