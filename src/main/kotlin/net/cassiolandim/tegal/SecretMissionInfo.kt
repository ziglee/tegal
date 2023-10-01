package net.cassiolandim.tegal

data class SecretMissionInfo(
    val name: String,
    val threeOrPlusPlayersOnly: Boolean,
    val text: String,
    val additionalText: String,
) {
    companion object {
        val charger = SecretMissionInfo(
            name = "CHARGER",
            threeOrPlusPlayersOnly = true,
            text = "Gain <points:3> if you have the most <energy> at the end of the game.",
            additionalText = "If you are tied for the most, gain only <points:2>."
        )
        val conqueror = SecretMissionInfo(
            name = "CONQUEROR",
            threeOrPlusPlayersOnly = true,
            text = "Gain <points:3> if you have the most planets at the end of the game.",
            additionalText = "If you are tied for the most, gain only <points:2>."
        )
        val elder = SecretMissionInfo(
            name = "ELDER",
            threeOrPlusPlayersOnly = true,
            text = "Gain <points:2> if you trigger the end of the game.",
            additionalText = "If you are tied for the most, gain only <points:2>."
        )
    }
}