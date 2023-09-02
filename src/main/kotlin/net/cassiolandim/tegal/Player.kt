package net.cassiolandim.tegal

data class Player(
    var name: String,
    var energyTokens: Int = 2,
    var cultureTokens: Int = 1,
    var empireTokens: Int = 0,
    var activeDice: Int = 4,
    var activeShips: Int = 2,
) : ShipLocation {
    val ships = mutableSetOf(
        Ship(this, this),
        Ship(this, this),
    )
}
