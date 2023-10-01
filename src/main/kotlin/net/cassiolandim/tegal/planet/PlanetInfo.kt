package net.cassiolandim.tegal.planet

import net.cassiolandim.tegal.Game

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
    val actionText: String,
) {
    companion object {
        val andellouxian6 = PlanetInfo(
            name = "ANDELLOUXIAN-6",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 4,
            pointsWorth = 5,
            actionText = "Move 1 of your ships from a planet\'s orbit to your galaxy, then acquire energy and/or culture equal to that ship\'s orbit number.",
        )
        val aughmoore = PlanetInfo(
            name = "AUGHMOORE",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 5,
            pointsWorth = 7,
            actionText = "Acquire culture for every ship landed in your galaxy.",
        )
        val birkomius = PlanetInfo(
            name = "BIRKOMIUS",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 1,
            pointsWorth = 1,
            actionText = "On your turn after utilizing this colony, if you are followed then acquire 1 culture per follow.",
        )
        val bisschop = PlanetInfo(
            name = "BISSCHOP",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 1,
            pointsWorth = 1,
            actionText = "On your turn after utilizing this colony, if you are followed then acquire 1 energy per follow.",
        )
        val brumbaugh = PlanetInfo(
            name = "BRUMBAUGH",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 3,
            pointsWorth = 3,
            actionText = "Spend 2 energy to regress 2 enemy ships by -1.",
        )
        val bsw101 = PlanetInfo(
            name = "BSW-10-1",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 4,
            pointsWorth = 5,
            actionText = "Regress one of your ships -1, then advance another one of your ships +1.",
        )
        val clj0517 = PlanetInfo(
            name = "CLJ-0517",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 2,
            pointsWorth = 2,
            actionText = "Steal 1 culture from another player (only once during your turn).",
        )
        val drewkaiden = PlanetInfo(
            name = "DREWKAIDEN",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 1,
            pointsWorth = 1,
            actionText = "Advance +1 diplomacy.",
        )
        val gleamzanier = PlanetInfo(
            name = "GLEAM-ZANIER",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 4,
            pointsWorth = 5,
            actionText = "Acquired 2 energy, all other players acquire 1 energy.",
        )
        val gort = PlanetInfo(
            name = "GORT",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 5,
            pointsWorth = 7,
            actionText = "Move 1 of your orbiting ships to an equal number of another planet\'s orbit (this may colonize the planet).",
        )
        val gyore = PlanetInfo(
            name = "GYORE",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 5,
            pointsWorth = 7,
            actionText = "Set 1 inactive die to a face of your choice.",
        )
        val helios = PlanetInfo(
            name = "HELIOS",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 2,
            pointsWorth = 2,
            actionText = "Place an un-occupied planet from the center row into the bottom of the planet deck draw a new planet.",
        )
        val hoefker = PlanetInfo(
            name = "HOEFKER",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 2,
            pointsWorth = 2,
            actionText = "Spend 1 energy to acquire 2 culture.",
        )
        val jac110912 = PlanetInfo(
            name = "JAC-110912",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 4,
            pointsWorth = 5,
            actionText = "Acquire 2 culture, all other players acquire 2 culture.",
        )
        val jakks = PlanetInfo(
            name = "JAKKS",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 1,
            pointsWorth = 1,
            actionText = "Acquire 1 culture.",
        )
        val jorg = PlanetInfo(
            name = "JORG",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 3,
            pointsWorth = 3,
            actionText = "Spend 2 culture to regress 1 enemy ship by -2.",
        )
        val kwidow = PlanetInfo(
            name = "K-WIDOW",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 5,
            pointsWorth = 7,
            actionText = "Regress an enemy ship by -1.",
        )
        val latorres = PlanetInfo(
            name = "LA-TORRES",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 2,
            pointsWorth = 2,
            actionText = "Steal 1 energy from another player (only once during your turn).",
        )
        val leandra = PlanetInfo(
            name = "LEANDRA",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 1,
            pointsWorth = 1,
            actionText = "Advance +1 economy.",
        )
        val lureena = PlanetInfo(
            name = "LUREENA",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 2,
            pointsWorth = 2,
            actionText = "Upgrade your empire, you may spend a mix of energy and culture.",
        )
        val maia = PlanetInfo(
            name = "MAIA",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 4,
            pointsWorth = 5,
            actionText = "Discard 2 inactive dice, acquire 2 energy and 2 culture.",
        )
        val mared = PlanetInfo(
            name = "MARED",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 2,
            pointsWorth = 2,
            actionText = "If your empire level is the lowset (or tied for lowest) upgrade your empire for 1 less resource.",
        )
        val mj120210 = PlanetInfo(
            name = "MJ-120210",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 2,
            pointsWorth = 2,
            actionText = "Acquired 2 energy.",
        )
        val nagato = PlanetInfo(
            name = "NAGATO",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 3,
            pointsWorth = 3,
            actionText = "Spend 1 culture to move 2 of your ships (only once per turn).",
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
            jorg,
            kwidow,
            latorres,
            leandra,
            lureena,
            maia,
            mared,
            mj120210,
            nagato,
        )
    }
}