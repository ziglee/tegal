package net.cassiolandim.tegal

enum class Language {
    PT_BR,
    EN_US
}

enum class I18NStringKey {
    ANDELLOUXIAN6_ACTION,
    AUGHMOORE_ACTION,
    BIRKOMIUS_ACTION,
    BISSCHIOP_ACTION,
    BRUMGAUGH_ACTION,
    BSW101_ACTION,
    CLJ0517_ACTION,
    DREWKAIDEN_ACTION,
    GLEAMZANIER_ACTION,
    GORT_ACTION,
    GYORE_ACTION,
    HELIOS_ACTION,
    HOEFKER_ACTION,
    JAC110912_ACTION,
    JAKKS_ACTION,
    JORG_ACTION,
    KWIDOW_ACTION,
    LATORRES_ACTION,
    LEANDRA_ACTION,
    LUREENA_ACTION,
    MAIA_ACTION,
    MARED_ACTION,
    MJ120210_ACTION,
    NAGATO_ACTION,
}

val i18n: Map<I18NStringKey, Map<Language, String>> = mapOf(
    I18NStringKey.ANDELLOUXIAN6_ACTION to mapOf(
        Language.EN_US to "Move 1 of your ships from a planet\'s orbit to your galaxy, then acquire energy and/or culture equal to that ship\'s orbit number.",
        Language.PT_BR to "Mova 1 de suas naves de uma órbita para sua galáxia, então adquira uma energia e/ou cultura igual ao número da órbita daquela nave."
    ),
    I18NStringKey.AUGHMOORE_ACTION to mapOf(
        Language.EN_US to "Acquire culture for every ship landed in your galaxy.",
        Language.PT_BR to ""
    ),
    I18NStringKey.BIRKOMIUS_ACTION to mapOf(
        Language.EN_US to "On your turn after utilizing this colony, if you are followed then acquire 1 culture per follow.",
        Language.PT_BR to ""
    ),
    I18NStringKey.BISSCHIOP_ACTION to mapOf(
        Language.EN_US to "On your turn after utilizing this colony, if you are followed then acquire 1 energy per follow.",
        Language.PT_BR to ""
    ),
    I18NStringKey.BRUMGAUGH_ACTION to mapOf(
        Language.EN_US to "Spend 2 energy to regress 2 enemy ships by -1.",
        Language.PT_BR to ""
    ),
    I18NStringKey.BSW101_ACTION to mapOf(
        Language.EN_US to "Regress one of your ships -1, then advance another one of your ships +1.",
        Language.PT_BR to ""
    ),
    I18NStringKey.CLJ0517_ACTION to mapOf(
        Language.EN_US to "Steal 1 culture from another player (only once during your turn).",
        Language.PT_BR to ""
    ),
    I18NStringKey.DREWKAIDEN_ACTION to mapOf(
        Language.EN_US to "Advance +1 diplomacy.",
        Language.PT_BR to ""
    ),
    I18NStringKey.GLEAMZANIER_ACTION to mapOf(
        Language.EN_US to "Acquired 2 energy, all other players acquire 1 energy.",
        Language.PT_BR to ""
    ),
    I18NStringKey.GORT_ACTION to mapOf(
        Language.EN_US to "Move 1 of your orbiting ships to an equal number of another planet\'s orbit (this may colonize the planet).",
        Language.PT_BR to ""
    ),
    I18NStringKey.GYORE_ACTION to mapOf(
        Language.EN_US to "Set 1 inactive die to a face of your choice.",
        Language.PT_BR to ""
    ),
    I18NStringKey.HELIOS_ACTION to mapOf(
        Language.EN_US to "Place an un-occupied planet from the center row into the bottom of the planet deck draw a new planet.",
        Language.PT_BR to ""
    ),
    I18NStringKey.HOEFKER_ACTION to mapOf(
        Language.EN_US to "Spend 1 energy to acquire 2 culture.",
        Language.PT_BR to ""
    ),
    I18NStringKey.JAC110912_ACTION to mapOf(
        Language.EN_US to "Acquire 2 culture, all other players acquire 2 culture.",
        Language.PT_BR to ""
    ),
    I18NStringKey.JAKKS_ACTION to mapOf(
        Language.EN_US to "Acquire 1 culture.",
        Language.PT_BR to ""
    ),
    I18NStringKey.JORG_ACTION to mapOf(
        Language.EN_US to "Spend 2 culture to regress 1 enemy ship by -2.",
        Language.PT_BR to ""
    ),
    I18NStringKey.KWIDOW_ACTION to mapOf(
        Language.EN_US to "Regress an enemy ship by -1.",
        Language.PT_BR to ""
    ),
    I18NStringKey.LATORRES_ACTION to mapOf(
        Language.EN_US to "Steal 1 energy from another player (only once during your turn).",
        Language.PT_BR to ""
    ),
    I18NStringKey.LEANDRA_ACTION to mapOf(
        Language.EN_US to "Advance +1 economy.",
        Language.PT_BR to ""
    ),
    I18NStringKey.LUREENA_ACTION to mapOf(
        Language.EN_US to "Upgrade your empire, you may spend a mix of energy and culture.",
        Language.PT_BR to ""
    ),
    I18NStringKey.MAIA_ACTION to mapOf(
        Language.EN_US to "Discard 2 inactive dice, acquire 2 energy and 2 culture.",
        Language.PT_BR to ""
    ),
    I18NStringKey.MARED_ACTION to mapOf(
        Language.EN_US to "If your empire level is the lowset (or tied for lowest) upgrade your empire for 1 less resource.",
        Language.PT_BR to ""
    ),
    I18NStringKey.MJ120210_ACTION to mapOf(
        Language.EN_US to "Acquired 2 energy.",
        Language.PT_BR to ""
    ),
    I18NStringKey.NAGATO_ACTION to mapOf(
        Language.EN_US to "Spend 1 culture to move 2 of your ships (only once per turn).",
        Language.PT_BR to ""
    ),
)
