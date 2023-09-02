package net.cassiolandim.tegal

class Game(
    val players: Set<Player>,
    val planets: MutableSet<Planet>,
) {
    private var currentPlayerIndex: Int = 0
}