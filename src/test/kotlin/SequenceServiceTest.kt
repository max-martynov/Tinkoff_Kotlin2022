import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import util.TestData
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


class SequenceServiceTest {

    val sequenceService = SequenceService()

    private val basicVehiclesWithDescription = TestData.basicVehiclesWithDescription.asSequence()

    private val basicVehicles: List<Vehicle>
        get() = basicVehiclesWithDescription.map { it.vehicle }.toList()

    private val basicDescriptions: List<String>
        get() = basicVehiclesWithDescription.map { it.description }.toList()


    @Nested
    inner class GetDescriptionsAndSortByPriceTest {

        @Test
        fun `empty list remains unchanged`() {
            assertContentEquals(emptySequence(), sequenceService.getDescriptionsAndSortByPrice(emptySequence()))
        }

        @Test
        fun `basic vehicles are sorted in right order`() {
            val sortedByPrice = listOf(
                basicDescriptions[1],
                basicDescriptions[3],
                basicDescriptions[2],
                basicDescriptions[0]
            )
            assertContentEquals(sortedByPrice.asSequence(), sequenceService.getDescriptionsAndSortByPrice(basicVehicles.asSequence()))
        }

    }

    @Nested
    inner class GroupByBodyTypeTest {

        @Test
        fun `empty list remains unchanged`() {
            assertEquals(mapOf(), sequenceService.groupByBodyType(emptySequence()))
        }

        @Test
        fun `all body types are distinct`() {
            val expected = mapOf(
                BodyType.Roadster to listOf(basicVehicles[0]),
                BodyType.Sedan to listOf(basicVehicles[1]),
                BodyType.Minivan to listOf(basicVehicles[2]),
                BodyType.Crossover to listOf(basicVehicles[3])
            )
            assertEquals(expected, sequenceService.groupByBodyType(basicVehicles.asSequence()))
        }

        @Test
        fun `a few vehicles of same body type`() {
            val sedans = sequenceOf(
                Vehicle("a", BodyType.Sedan, (0..100).random()),
                Vehicle("b", BodyType.Sedan, (0..100).random()),
                Vehicle("c", BodyType.Sedan, (0..100).random())
            )
            assertEquals(mapOf(BodyType.Sedan to sedans.toList()), sequenceService.groupByBodyType(sedans))
        }

    }

    @Nested
    inner class FilterAndTakeThreeTest {

        @Test
        fun `empty list remains unchanged`() {
            assertContentEquals(emptySequence(), sequenceService.filterAndTakeThree(emptySequence()) { true })
        }

        @Test
        fun `list of size less than 3 with always true predicate`() {
            assertContentEquals(basicDescriptions.take(2).asSequence(),
                sequenceService.filterAndTakeThree(
                    basicVehicles.take(2).asSequence()
                ) { true }
            )
        }

        @Test
        fun `list of size greater than 3 with always true predicate`() {
            assertContentEquals(basicDescriptions.take(3).asSequence(), sequenceService.filterAndTakeThree(basicVehicles.asSequence()) { true })
        }

        @Test
        fun `always false predicate returns empty list`() {
            assertContentEquals(emptySequence(), sequenceService.filterAndTakeThree(basicVehicles.asSequence()) { false })
        }

        @Test
        fun `some custom predicate`() {
            val expected = sequenceOf(
                basicDescriptions[0],
                basicDescriptions[2],
                basicDescriptions[3]
            )
            assertContentEquals(expected, sequenceService.filterAndTakeThree(basicVehicles.asSequence()) { it.priceInRubles > 30 })
        }
    }

}
