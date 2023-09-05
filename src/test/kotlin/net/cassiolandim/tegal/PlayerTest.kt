package net.cassiolandim.tegal

import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerTest {

    @Test
    fun assert_default_values() {
        val player = Player("Cássio")
        assertEquals("Cássio", player.name)
        assertEquals(1, player.empireTokens)
        assertEquals(1, player.cultureTokens)
        assertEquals(2, player.energyTokens)
        assertEquals(4, player.activeDice)
        assertEquals(0, player.galaxyPoints)
        assertEquals(2, player.ships.size)
    }

    @Test
    fun when_incrementing_empire_tokens() {
        val player = Player("Cássio")

        player.incrementEmpireTokens()
        assertEquals(2, player.empireTokens)
        assertEquals(5, player.activeDice)
        assertEquals(2, player.ships.size)
        assertEquals(1, player.galaxyPoints)

        player.incrementEmpireTokens()
        assertEquals(3, player.empireTokens)
        assertEquals(5, player.activeDice)
        assertEquals(3, player.ships.size)
        assertEquals(2, player.galaxyPoints)

        player.incrementEmpireTokens()
        assertEquals(4, player.empireTokens)
        assertEquals(6, player.activeDice)
        assertEquals(3, player.ships.size)
        assertEquals(3, player.galaxyPoints)

        player.incrementEmpireTokens()
        assertEquals(5, player.empireTokens)
        assertEquals(6, player.activeDice)
        assertEquals(4, player.ships.size)
        assertEquals(5, player.galaxyPoints)

        player.incrementEmpireTokens()
        assertEquals(6, player.empireTokens)
        assertEquals(7, player.activeDice)
        assertEquals(4, player.ships.size)
        assertEquals(8, player.galaxyPoints)
    }
}