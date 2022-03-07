import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ForwardTest {

    private val ordinaryForward = Forward("Paulo Dybala", 28, "Argentina")
    private val expensiveForward = Forward("Cristiano Ronaldo", 38, "Portugal")

    @Test
    fun `increment goals`() {
        assertEquals(0, ordinaryForward.goalsScored)
        ordinaryForward.incrementGoals()
        assertEquals(1, ordinaryForward.goalsScored)
    }

    @Test
    fun `calculate transfer cost for ordinary forward (ie not from Portugal)`() {
        assertEquals(0, ordinaryForward.calculateTransferCost())
        ordinaryForward.incrementGoals()
        assertEquals(ordinaryForward.age * 1000, ordinaryForward.calculateTransferCost())
        ordinaryForward.incrementGoals()
        assertEquals(ordinaryForward.age * 1000 * 2, ordinaryForward.calculateTransferCost())
    }

    @Test
    fun `calculate transfer cost for expensive forward (ie from Portugal)`() {
        assertEquals(0, expensiveForward.calculateTransferCost())
        expensiveForward.incrementGoals()
        assertEquals(expensiveForward.age * 1000 * 10, expensiveForward.calculateTransferCost())
        expensiveForward.incrementGoals()
        assertEquals(expensiveForward.age * 1000 * 2 * 10, expensiveForward.calculateTransferCost())
    }

}

