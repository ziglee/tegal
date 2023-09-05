package net.cassiolandim.tegal

import java.util.*
import kotlin.random.Random

data class Die(
    val faceUp: DieFace = DieFace.entries.toTypedArray()[Random.nextInt(5)],
    val id: UUID = UUID.randomUUID(),
) {
    companion object {
        fun roll(num: Int): List<Die> {
            return (1..num).map { Die() }
        }
    }
}
