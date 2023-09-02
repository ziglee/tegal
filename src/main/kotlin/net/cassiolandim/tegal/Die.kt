package net.cassiolandim.tegal

import kotlin.random.Random

data class Die(
    var faceUp: DieFace = DieFace.MOVE_SHIP
) {
    fun roll() {
        faceUp = DieFace.entries.toTypedArray()[Random.nextInt(5)]
    }
}
