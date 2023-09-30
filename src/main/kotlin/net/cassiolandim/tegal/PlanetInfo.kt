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
    val action: I18NStringKey,
) {
    companion object {
        val andellouxian6 = PlanetInfo(
            name = "ANDELLOUXIAN-6",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 4,
            pointsWorth = 5,
            action = I18NStringKey.ANDELLOUXIAN6_ACTION,
        )
        val aughmoore = PlanetInfo(
            name = "AUGHMOORE",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 5,
            pointsWorth = 7,
            action = I18NStringKey.AUGHMOORE_ACTION,
        )
        val birkomius = PlanetInfo(
            name = "BIRKOMIUS",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 1,
            pointsWorth = 1,
            action = I18NStringKey.BIRKOMIUS_ACTION,
        )
        val bisschop = PlanetInfo(
            name = "BISSCHOP",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 1,
            pointsWorth = 1,
            action = I18NStringKey.BISSCHIOP_ACTION,
        )
        val brumbaugh = PlanetInfo(
            name = "BRUMBAUGH",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 3,
            pointsWorth = 3,
            action = I18NStringKey.BRUMGAUGH_ACTION,
        )
        val bsw101 = PlanetInfo(
            name = "BSW-10-1",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 4,
            pointsWorth = 5,
            action = I18NStringKey.BSW101_ACTION,
        )
        val clj0517 = PlanetInfo(
            name = "CLJ-0517",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 2,
            pointsWorth = 2,
            action = I18NStringKey.CLJ0517_ACTION,
        )
        val drewkaiden = PlanetInfo(
            name = "DREWKAIDEN",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 1,
            pointsWorth = 1,
            action = I18NStringKey.DREWKAIDEN_ACTION,
        )
        val gleamzanier = PlanetInfo(
            name = "GLEAM-ZANIER",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 4,
            pointsWorth = 5,
            action = I18NStringKey.GLEAMZANIER_ACTION,
        )
        val gort = PlanetInfo(
            name = "GORT",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 5,
            pointsWorth = 7,
            action = I18NStringKey.GORT_ACTION,
        )
        val gyore = PlanetInfo(
            name = "GYORE",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 5,
            pointsWorth = 7,
            action = I18NStringKey.GYORE_ACTION,
        )
        val helios = PlanetInfo(
            name = "HELIOS",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 2,
            pointsWorth = 2,
            action = I18NStringKey.HELIOS_ACTION,
        )
        val hoefker = PlanetInfo(
            name = "HOEFKER",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 2,
            pointsWorth = 2,
            action = I18NStringKey.HOEFKER_ACTION,
        )
        val jac110912 = PlanetInfo(
            name = "JAC-110912",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 4,
            pointsWorth = 5,
            action = I18NStringKey.JAC110912_ACTION,
        )
        val jakks = PlanetInfo(
            name = "JAKKS",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 1,
            pointsWorth = 1,
            action = I18NStringKey.JAKKS_ACTION,
        )
        val jorg = PlanetInfo(
            name = "JORG",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 3,
            pointsWorth = 3,
            action = I18NStringKey.JORG_ACTION,
        )
        val kwidow = PlanetInfo(
            name = "K-WIDOW",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 5,
            pointsWorth = 7,
            action = I18NStringKey.KWIDOW_ACTION,
        )
        val latorres = PlanetInfo(
            name = "LA-TORRES",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 2,
            pointsWorth = 2,
            action = I18NStringKey.LATORRES_ACTION,
        )
        val leandra = PlanetInfo(
            name = "LEANDRA",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 1,
            pointsWorth = 1,
            action = I18NStringKey.LEANDRA_ACTION,
        )
        val lureena = PlanetInfo(
            name = "LUREENA",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 2,
            pointsWorth = 2,
            action = I18NStringKey.LUREENA_ACTION,
        )
        val maia = PlanetInfo(
            name = "MAIA",
            productionType = PlanetProductionType.CULTURE,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 4,
            pointsWorth = 5,
            action = I18NStringKey.MAIA_ACTION,
        )
        val mared = PlanetInfo(
            name = "MARED",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 2,
            pointsWorth = 2,
            action = I18NStringKey.MARED_ACTION,
        )
        val mj120210 = PlanetInfo(
            name = "MJ-120210",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.DIPLOMACY,
            trackLength = 2,
            pointsWorth = 2,
            action = I18NStringKey.MJ120210_ACTION,
        )
        val nagato = PlanetInfo(
            name = "NAGATO",
            productionType = PlanetProductionType.ENERGY,
            trackType = PlanetTrackType.ECONOMY,
            trackLength = 3,
            pointsWorth = 3,
            action = I18NStringKey.NAGATO_ACTION,
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
            nagato,
        )
    }
}