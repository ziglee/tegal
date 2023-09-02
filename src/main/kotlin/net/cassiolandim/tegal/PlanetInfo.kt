package net.cassiolandim.tegal

data class PlanetInfo(
    val name: String,
    val productionType: PlanetProductionType,
    val trackType: PlanetTrackType,
    val trackLength: Int,
    val pointsWorth: Int,
//    val action: Any,
) {
    fun createPlanet() = Planet(this)

    companion object {
        val aughmoore = PlanetInfo(
            name = "Aughmoore",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 5,
            pointsWorth = 7,
        )
        val drewkaiden = PlanetInfo(
            name = "Drewkaiden",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 1,
            pointsWorth = 1,
        )
        val gort = PlanetInfo(
            name = "Gort",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 5,
            pointsWorth = 7,
        )
        val maia = PlanetInfo(
            name = "Maia",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 4,
            pointsWorth = 5,
        )
        val nagato = PlanetInfo(
            name = "Nagato",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 3,
            pointsWorth = 3,
        )

        val planets = setOf(
            aughmoore,
            drewkaiden,
            gort,
            maia,
            nagato
        )
    }
}