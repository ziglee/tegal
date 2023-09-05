package net.cassiolandim.tegal

fun MutableSet<PlanetInfo>.takeRandom(game: Game, x: Int): MutableSet<Planet> {
    val mSet = mutableSetOf<Planet>()
    var counter = 0
    while (counter < x) {
        mSet.add(takeRandom(game))
        counter++
    }
    return mSet
}

fun MutableSet<PlanetInfo>.takeRandom(game: Game): Planet {
    val item = random()
    remove(item)
    return Planet(game, item)
}

data class PlanetInfo(
    val name: String,
    val productionType: PlanetProductionType,
    val trackType: PlanetTrackType,
    val trackLength: Int,
    val pointsWorth: Int,
//    val action: Any,
) {
    companion object {
        val andellouxian6 = PlanetInfo(
            name = "Andellouxian-6",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 4,
            pointsWorth = 5,
        )
        val aughmoore = PlanetInfo(
            name = "Aughmoore",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 5,
            pointsWorth = 7,
        )
        val birkomius = PlanetInfo(
            name = "Birkomius",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 1,
            pointsWorth = 1,
        )
        val bisschop = PlanetInfo(
            name = "Bisschop",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 1,
            pointsWorth = 1,
        )
        val brumbaugh = PlanetInfo(
            name = "Brumbaugh",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 3,
            pointsWorth = 3,
        )
        val bsw101 = PlanetInfo(
            name = "BSW-10-1",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 4,
            pointsWorth = 5,
        )
        val clj0517 = PlanetInfo(
            name = "BSW-10-1",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 2,
            pointsWorth = 2,
        )
        val drewkaiden = PlanetInfo(
            name = "Drewkaiden",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 1,
            pointsWorth = 1,
        )
        val gleamzanier = PlanetInfo(
            name = "Gleam-zanier",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 4,
            pointsWorth = 5,
        )
        val gort = PlanetInfo(
            name = "Gort",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 5,
            pointsWorth = 7,
        )
        val gyore = PlanetInfo(
            name = "Gyore",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 5,
            pointsWorth = 7,
        )
        val helios = PlanetInfo(
            name = "Helios",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 2,
            pointsWorth = 2,
        )
        val hoefker = PlanetInfo(
            name = "Hoefker",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 2,
            pointsWorth = 2,
        )
        val jac110912 = PlanetInfo(
            name = "Jac-110912",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 4,
            pointsWorth = 5,
        )
        val jakks = PlanetInfo(
            name = "Jakks",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 1,
            pointsWorth = 1,
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

        fun planets() = mutableSetOf(
            andellouxian6,
            aughmoore,
            birkomius,
            bisschop,
            brumbaugh,
            bsw101,
            clj0517,
            drewkaiden,
            gleamzanier,
            gort,
            gyore,
            helios,
            hoefker,
            jac110912,
            jakks,
            maia,
            nagato
        )
    }
}