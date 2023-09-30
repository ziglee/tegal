package net.cassiolandim.tegal

import net.cassiolandim.tegal.exceptions.IllegalMoveException
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerTest {

    @Test
    fun assert_default_values() {
        val player = Player("Cássio")
        assertEquals("Cássio", player.name)
        assertEquals(1, player.empireLevel)
        assertEquals(1, player.cultureLevel)
        assertEquals(2, player.energyLevel)
        assertEquals(4, player.diceCount)
        assertEquals(0, player.galaxyPoints)
        assertEquals(2, player.ships.size)
    }

    @Test
    fun when_incrementing_empire_tokens() {
        val player = Player("Cássio")

        player.incrementEmpireTokens()
        assertEquals(2, player.empireLevel)
        assertEquals(2, player.ships.size)
        assertEquals(1, player.galaxyPoints)

        player.incrementEmpireTokens()
        assertEquals(3, player.empireLevel)
        assertEquals(3, player.ships.size)
        assertEquals(2, player.galaxyPoints)

        player.incrementEmpireTokens()
        assertEquals(4, player.empireLevel)
        assertEquals(3, player.ships.size)
        assertEquals(3, player.galaxyPoints)

        player.incrementEmpireTokens()
        assertEquals(5, player.empireLevel)
        assertEquals(4, player.ships.size)
        assertEquals(5, player.galaxyPoints)

        player.incrementEmpireTokens()
        assertEquals(6, player.empireLevel)
        assertEquals(4, player.ships.size)
        assertEquals(8, player.galaxyPoints)
    }

    @Test
    fun when_spending_energy_tokens() {
        val player = Player("Cássio")
        val energyLevel = player.energyLevel
        val amount = 1

        player.spendEnergy(amount)

        assertEquals(energyLevel - amount, player.energyLevel)
    }

    @Test
    fun when_spending_culture_tokens() {
        val player = Player("Cássio")
        val cultureLevel = player.cultureLevel
        val amount = 1

        player.spendCulture(amount)

        assertEquals(cultureLevel - amount, player.cultureLevel)
    }

    @Test(expected = IllegalMoveException::class)
    fun given_player_has_not_enough_tokens_when_spending_culture_tokens() {
        Player("Cássio").spendCulture(4)
    }

    @Test(expected = IllegalMoveException::class)
    fun given_player_has_not_enough_tokens_when_spending_energy_tokens() {
        Player("Cássio").spendCulture(4)
    }
}