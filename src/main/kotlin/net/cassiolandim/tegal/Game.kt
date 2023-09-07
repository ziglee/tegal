package net.cassiolandim.tegal

import net.cassiolandim.tegal.exceptions.EntityNotFoundException
import net.cassiolandim.tegal.exceptions.IllegalMoveException
import java.util.*

class Game(
    vararg playersNames: String,
) {
    val players: List<Player> = playersNames.map { Player(it) }
    private var currentPlayerIndex: Int = 0
    private val poolOfPlanets = PlanetInfo.planets()
    private val _planetsInGame = poolOfPlanets.takeRandom(
        this,
        if (players.size == 5) 6 else players.size + 2
    )
    val planetsInGame: Set<Planet>
        get() = _planetsInGame
    val currentPlayer: Player
        get() = players[currentPlayerIndex]
    var rolledDice = listOf<Die>()
    var activateDie: Die? = null
    var activationBay = listOf<Die>()
    var hasConvertedDice = false
    var hasUsedFreeReroll = false

    fun removePlanetFromGame(planet: Planet) {
        _planetsInGame.remove(planet)
        if (poolOfPlanets.isNotEmpty()) {
            _planetsInGame.add(
                poolOfPlanets.takeRandom(this)
            )
        }
    }

    fun rollDice() {
        if (rolledDice.isNotEmpty())  throw IllegalMoveException("Player already rolled his dice")
        rolledDice = Die.roll(currentPlayer.activeDice)
    }

    fun fakeRollDiceAllMoveUp() {
        rolledDice = (1..currentPlayer.activeDice).map {
            Die(faceUp = DieFace.MOVE_SHIP)
        }
    }

    fun freeRerollDice() {
        if (rolledDice.isEmpty())  throw IllegalMoveException("There are no dice left to be re-rolled")
        if (hasUsedFreeReroll) throw IllegalMoveException("Player already used its free re-roll this turn")
        rolledDice = Die.roll(rolledDice.size)
        hasUsedFreeReroll = true
    }

    fun paidReroll(diceToRerollIds: Set<UUID>) {
        if (rolledDice.isEmpty())  throw IllegalMoveException("There are no dice left to be re-rolled")

        currentPlayer.spendEnergyTokens(diceToRerollIds.size)

        val remainingDice = rolledDice.filterNot { diceToRerollIds.contains(it.id) }
        rolledDice = remainingDice + Die.roll(diceToRerollIds.size)
    }

    fun activateDieMoveShipToPlanetSurface(dieId: UUID, shipId: UUID, planetId: UUID) {
        val die = rolledDice.findById(dieId)
        if (die.faceUp != DieFace.MOVE_SHIP) throw IllegalMoveException("Chosen die has wrong face up")
        val ship = currentPlayer.ships.findById(shipId)
        val planet = planetsInGame.findById(planetId)

        ship.leaveOldLocationAndMoveToPlanetSurface(planet)

        activateDie = die
        activationBay += die
        rolledDice -= die
    }
}

fun List<Player>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Player $id not found")
fun List<Die>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Die $id not found")
fun Set<Ship>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Ship $id not found")
fun Set<Planet>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Planet $id not found")
