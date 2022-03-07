import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class FootballTeamTest {

    private val forwardMock: Forward = mockk()
    private val goalKeeperMock: GoalKeeper = mockk()

    @Test
    fun `empty football team`() {
        val footballTeam = FootballTeam(listOf())
        assertEquals(0, footballTeam.calculateTotalCost())
    }

    @Test
    fun `football team consisting of one player`() {
        every { forwardMock.calculateTransferCost() } returns 1000
        val footballTeam = FootballTeam(listOf(forwardMock))
        assertEquals(1000, footballTeam.calculateTotalCost())
        verify(exactly = 1) {
            forwardMock.calculateTransferCost()
        }
    }

    @Test
    fun `football team consisting of two different players`() {
        every { forwardMock.calculateTransferCost() } returns 2000
        every { goalKeeperMock.calculateTransferCost() } returns 3000
        val footballTeam = FootballTeam(listOf(forwardMock, goalKeeperMock))
        assertEquals(5000, footballTeam.calculateTotalCost())
        verify(exactly = 1) {
            forwardMock.calculateTransferCost()
            goalKeeperMock.calculateTransferCost()
        }
    }

    @Test
    fun `football team consisting of two same players`() {
        every { goalKeeperMock.calculateTransferCost() } returns 3000
        val footballTeam = FootballTeam(listOf(goalKeeperMock, goalKeeperMock))
        assertEquals(6000, footballTeam.calculateTotalCost())
        verify(exactly = 2) {
            goalKeeperMock.calculateTransferCost()
        }
    }

    @Test
    fun `football team consisting of many random players`() {
        val players: List<FootballPlayer> = List(1000) { mockk() }
        var sum = 0
        players.forEach {
            val transferCost = (1..1000).random()
            every { it.calculateTransferCost() } returns transferCost
            sum += transferCost
        }
        val footballTeam = FootballTeam(players)
        assertEquals(sum, footballTeam.calculateTotalCost())
        players.forEach { verify(exactly = 1) { it.calculateTransferCost() } }
    }

}
