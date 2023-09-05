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

    fun removePlanetFromGame(planet: Planet) {
        _planetsInGame.remove(planet)
        if (poolOfPlanets.isNotEmpty()) {
            _planetsInGame.add(
                poolOfPlanets.takeRandom(this)
            )
        }
    }

    fun rollDice() {
        rolledDice = Die.roll(currentPlayer.activeDice)
    }

    fun activateMoveShipDie(dieId: UUID) {
        val die = rolledDice.findById(dieId)
        if (die.faceUp != DieFace.MOVE_SHIP) throw IllegalMoveException("Chosen die has wrong face up")

        activateDie = die

        // TODO
    }
}

fun List<Player>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Player $id not found")
fun List<Die>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Die $id not found")
fun Set<Ship>.findById(id: UUID) = find { it.id == id } ?: throw EntityNotFoundException("Ship $id not found")
