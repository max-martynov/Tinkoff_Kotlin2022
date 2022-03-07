import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class GoalKeeperTest {

    private val ordinaryGoalKeeper = GoalKeeper("Gianluigi Buffon", 44, "Italy")
    private val expensiveGoalKeeper = GoalKeeper("Iker Casillas", 40, "Spain")

    @Test
    fun `increment saves`() {
        assertEquals(0, ordinaryGoalKeeper.saves)
        ordinaryGoalKeeper.incrementSaves()
        assertEquals(1, ordinaryGoalKeeper.saves)
    }

    @Test
    fun `calculate transfer cost for ordinary goalkeeper (ie not from Spain)`() {
        assertEquals(0, ordinaryGoalKeeper.calculateTransferCost())
        ordinaryGoalKeeper.incrementSaves()
        assertEquals(ordinaryGoalKeeper.age * 1000, ordinaryGoalKeeper.calculateTransferCost())
        ordinaryGoalKeeper.incrementSaves()
        assertEquals(ordinaryGoalKeeper.age * 1000 * 2, ordinaryGoalKeeper.calculateTransferCost())
    }

    @Test
    fun `calculate transfer cost for expensive goalkeeper (ie from Spain)`() {
        assertEquals(0, expensiveGoalKeeper.calculateTransferCost())
        expensiveGoalKeeper.incrementSaves()
        assertEquals(expensiveGoalKeeper.age * 1000 * 10, expensiveGoalKeeper.calculateTransferCost())
        expensiveGoalKeeper.incrementSaves()
        assertEquals(expensiveGoalKeeper.age * 1000 * 2 * 10, expensiveGoalKeeper.calculateTransferCost())
    }

}