package net.cassiolandim.tegal

import net.cassiolandim.tegal.exceptions.EntityNotFoundException
import net.cassiolandim.tegal.exceptions.IllegalMoveException
import java.util.*

class Game(
    vararg playersNames: String,
) {
    companion object {
        private const val MIN_VICTORY_POINTS_TO_TRIGGER_END_GAME = 21
    }

    val players: List<Player> = playersNames.map { Player(it) }
    val poolOfPlanets = PlanetInfo.planets()
    private val _planetsInGame = poolOfPlanets.takeRandom(
        this,
        if (players.size == 5) 6 else players.size + 2
    )

    var currentPlayerIndex: Int = 0
        private set
    var dicePairToConvert: Pair<Die, Die>? = null
        private set
    var diceToAddToPlayerAfterTurnEnd = 0
        private set
    var playerWhoTriggeredEndGame: Player? = null
        private set
    var rolledDice = Die.roll(currentPlayer.diceCount)
        private set
    var lastActivatedDie: Die? = null
        private set
    val activationBay = mutableListOf<Die>()
    var roundCount = 1
        private set
    var ended = false
        private set

    val planetsInGame: Set<Planet>
        get() = _planetsInGame
    val currentPlayer: Player
        get() = players[currentPlayerIndex]
    var hasUsedFreeReroll: Boolean = false
        private set

    fun removePlanetFromGame(planet: Planet) {
        _planetsInGame.remove(planet)
        if (poolOfPlanets.isNotEmpty()) {
            _planetsInGame.add(
                poolOfPlanets.takeRandom(this)
            )
        }
    }

    private fun rollDice() {
        if (rolledDice.isNotEmpty()) throw IllegalMoveException("Player already rolled his dice")
        rolledDice = Die.roll(currentPlayer.diceCount)
    }

    fun freeRerollDice() {
        if (rolledDice.isEmpty()) throw IllegalMoveException("There are no dice left to be re-rolled")
        if (hasUsedFreeReroll) throw IllegalMoveException("Player already used its free re-roll this turn")
        rolledDice = Die.roll(rolledDice.size)
        hasUsedFreeReroll = true
    }

    fun paidReroll(diceToRerollIds: Set<UUID>) {
        if (rolledDice.isEmpty()) throw IllegalMoveException("There are no dice left to be re-rolled")

        currentPlayer.spendEnergy(diceToRerollIds.size)

        val remainingDice = rolledDice.filterNot { diceToRerollIds.contains(it.id) }
        rolledDice = remainingDice + Die.roll(diceToRerollIds.size)
    }

    fun activateDieMoveShipToPlanetSurface(dieId: UUID, shipId: UUID, planetId: UUID) {
        val die = rolledDice.findById(dieId)
        if (die.faceUp != DieFace.MOVE_SHIP) throw IllegalMoveException("Chosen die has wrong face up")
        val ship = currentPlayer.ships.findById(shipId)
        val planet = planetsInGame.findById(planetId)
        ship.leaveOldLocationAndMoveToPlanetSurface(planet)
        afterDieActivation(die)
    }

    fun activateDieMoveShipToPlanetOrbit(dieId: UUID, shipId: UUID, planetId: UUID) {
        val die = rolledDice.findById(dieId)
        if (die.faceUp != DieFace.MOVE_SHIP) throw IllegalMoveException("Chosen die has wrong face up")
        val ship = currentPlayer.ships.findById(shipId)
        val planet = planetsInGame.findById(planetId)
        ship.leaveOldLocationAndMoveToPlanetOrbit(planet)
        afterDieActivation(die)
    }

    fun activateDieAcquireEnergy(dieId: UUID) {
        val die = rolledDice.findById(dieId)
        if (die.faceUp != DieFace.ACQUIRE_ENERGY) throw IllegalMoveException("Chosen die has wrong face up")
        val count = currentPlayer.ships.count { ship ->
            ship.currentLocation.let {
                when (it) {
                    is Player -> true
                    is Planet -> it.info.productionType == PlanetProductionType.ENERGY
                    is PlanetTrackProgress -> it.planet.info.productionType == PlanetProductionType.ENERGY
                    else -> false
                }
            }
        }
        currentPlayer.incrementEnergy(count)
        afterDieActivation(die)
    }

    fun activateDieAcquireCulture(dieId: UUID) {
        val die = rolledDice.findById(dieId)
        if (die.faceUp != DieFace.ACQUIRE_CULTURE) throw IllegalMoveException("Chosen die has wrong face up")
        val count = currentPlayer.ships.count { ship ->
            ship.currentLocation.let {
                when (it) {
                    is Planet -> it.info.productionType == PlanetProductionType.CULTURE
                    is PlanetTrackProgress -> it.planet.info.productionType == PlanetProductionType.CULTURE
                    else -> false
                }
            }
        }
        currentPlayer.incrementCulture(count)
        afterDieActivation(die)
    }

    fun activateDieAdvanceDiplomacy(dieId: UUID, shipId: UUID) {
        val die = rolledDice.findById(dieId)
        if (die.faceUp != DieFace.ADVANCE_DIPLOMACY) throw IllegalMoveException("Chosen die has wrong face up")
        val ship = currentPlayer.ships.findById(shipId)
        if (!currentPlayer.ships.contains(ship)) throw IllegalMoveException("Cannot move another player ship")
        with(ship.currentLocation) {
            if (this is PlanetTrackProgress && this.planet.info.trackType != PlanetTrackType.DIPLOMACY) throw IllegalMoveException(
                "Cannot advance diplomacy on this planet"
            )
        }
        ship.incrementProgressOnOrbit()
        afterDieActivation(die)
    }

    fun activateDieAdvanceEconomy(dieId: UUID, shipId: UUID) {
        val die = rolledDice.findById(dieId)
        if (die.faceUp != DieFace.ADVANCE_ECONOMY) throw IllegalMoveException("Chosen die has wrong face up")
        val ship = currentPlayer.ships.findById(shipId)
        if (!currentPlayer.ships.contains(ship)) throw IllegalMoveException("Cannot move another player ship")
        with(ship.currentLocation) {
            if (this is PlanetTrackProgress && this.planet.info.trackType != PlanetTrackType.ECONOMY) throw IllegalMoveException(
                "Cannot advance economy on this planet"
            )
        }
        ship.incrementProgressOnOrbit()
        afterDieActivation(die)
    }

    fun activateDieUpgradeEmpire(dieId: UUID, resourceType: PlanetProductionType) {
        val die = rolledDice.findById(dieId)
        if (die.faceUp != DieFace.UTILIZE_COLONY) throw IllegalMoveException("Chosen die has wrong face up")
        currentPlayer.upgradeEmpire(resourceType)
        if (currentPlayer.empireLevel == 2 ||
            currentPlayer.empireLevel == 4 ||
            currentPlayer.empireLevel == 6
        ) {
            diceToAddToPlayerAfterTurnEnd++
        }
        afterDieActivation(die)
    }

    fun activateDieUtilizeColonyAndellouxian6(dieId: UUID, planetId: UUID, shipToMoveId: UUID, energyToAcquire: Int, cultureToAcquire: Int) {
        val die = rolledDice.findById(dieId)
        validateBeforeActivateDieUtilizeColony(die, planetId, PlanetInfo.andellouxian6)

        if (energyToAcquire < 0) throw IllegalMoveException("Energy to acquire must be greater or equal to zero")
        if (cultureToAcquire < 0) throw IllegalMoveException("Culture to acquire must be greater or equal to zero")

        val ship = currentPlayer.ships.findById(shipToMoveId)
        if (!currentPlayer.ships.contains(ship)) throw IllegalMoveException("Cannot move another player ship")

        val currentLocation = ship.currentLocation
        if (currentLocation !is PlanetTrackProgress) throw IllegalMoveException("Chosen ship isn\'t on orbit of a planet")
        if ((energyToAcquire + cultureToAcquire) > currentLocation.progress) throw IllegalMoveException("Orbit number is lower than energy and culture to acquire")

        ship.leaveOldLocationAndMoveToPlayer()
        currentPlayer.incrementEnergy(energyToAcquire)
        currentPlayer.incrementCulture(cultureToAcquire)

        afterDieActivation(die)
    }

    private fun validateBeforeActivateDieUtilizeColony(die: Die, planetId: UUID, expectedPlanetInfo: PlanetInfo) {
        if (die.faceUp != DieFace.UTILIZE_COLONY) throw IllegalMoveException("Chosen die has wrong face up")
        val planet = planetsInGame.findById(planetId)
        if (planet.info != expectedPlanetInfo) throw IllegalMoveException("Planet id and instance doesn't match")
    }

    fun endTurn() {
        // TODO only allow to end turn when no one else wants to follow

        if (playerWhoTriggeredEndGame != null && players.indexOf(currentPlayer) == players.lastIndex) {
            // TODO check secret missions and score players
            ended = true
            return
        }

        currentPlayer.addDice(diceToAddToPlayerAfterTurnEnd)
        diceToAddToPlayerAfterTurnEnd = 0
        hasUsedFreeReroll = false
        dicePairToConvert = null
        activationBay.clear()
        lastActivatedDie = null
        currentPlayerIndex++
        rolledDice = emptyList()
        if (currentPlayerIndex > (players.size - 1)) {
            currentPlayerIndex = 0
            roundCount++
        }
        rollDice()
    }

    private fun checkEndOfGameTrigger() {
        if (playerWhoTriggeredEndGame != null) return
        if (currentPlayer.totalPoints >= MIN_VICTORY_POINTS_TO_TRIGGER_END_GAME) {
            playerWhoTriggeredEndGame = currentPlayer
        }
        playerWhoTriggeredEndGame = players.find { it.totalPoints >= MIN_VICTORY_POINTS_TO_TRIGGER_END_GAME }
    }

    private fun afterDieActivation(die: Die) {
        lastActivatedDie = die
        activationBay += die
        rolledDice -= die

        checkEndOfGameTrigger()
    }

    fun convertDie(dicePair: Pair<UUID, UUID>, dieToConvertId: UUID, faceToSet: DieFace) {
        if (dicePairToConvert != null) throw IllegalMoveException("Player already has converted die this turn")
        val die1 = rolledDice.findById(dicePair.first)
        val die2 = rolledDice.findById(dicePair.second)
        val dieToConvert = rolledDice.findById(dieToConvertId)

        dicePairToConvert = Pair(die1, die2)
        dieToConvert.faceUp = faceToSet
        rolledDice -= die1
        rolledDice -= die2
    }

    /**
     * FOR TESTING ONLY FUNCTIONS
     */

    fun fakeRollDiceAll(dieFace: DieFace) {
        rolledDice = (1..currentPlayer.diceCount).map {
            Die(faceUp = dieFace)
        }
    }

    fun replacePlanetsInGame(planets: Set<Planet>) {
        _planetsInGame.clear()
        _planetsInGame.addAll(planets)
    }
}

fun List<Player>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Player $id not found")
fun List<Die>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Die $id not found")
fun Set<Ship>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Ship $id not found")
fun Set<Planet>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Planet $id not found")
