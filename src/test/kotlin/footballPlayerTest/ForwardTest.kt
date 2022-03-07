package footballPlayerTest

import Forward
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ForwardTest {

    private val usualForward = Forward("Paulo Dybala", 28, "Argentina")
    private val expensiveForward = Forward("Cristiano Ronaldo", 38, "Portugal")

    @Test
    fun `incrementing goals`() {
        assertEquals(0, usualForward.goalsScored)
        usualForward.incrementGoals()
        assertEquals(1, usualForward.goalsScored)
    }

    @Test
    fun `gg wp`() {
        assertEquals(0, usualForward.goalsScored)
    }

}

/**
 * What i have to test?
 *  1. goals incrementing
 *  2.
 */