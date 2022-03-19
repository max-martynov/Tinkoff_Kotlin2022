import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import util.TestData
import kotlin.test.assertEquals


class IterableServiceTest {

    val iterableService = IterableService()

    val basicVehiclesWithDescription = TestData.basicVehiclesWithDescription

    val basicVehicles: List<Vehicle>
        get() = basicVehiclesWithDescription.map { it.vehicle }

    val basicDescriptions: List<String>
        get() = basicVehiclesWithDescription.map { it.description }


    @Nested
    inner class GetDescriptionsAndSortByPriceTest {

        @Test
        fun `empty list remains unchanged`() {
            assertEquals(listOf(), iterableService.getDescriptionsAndSortByPrice(listOf()))
        }

        @Test
        fun `basic vehicles are sorted in right order`() {
            val sortedByPrice = listOf(
                basicDescriptions[1],
                basicDescriptions[3],
                basicDescriptions[2],
                basicDescriptions[0]
            )
            assertEquals(sortedByPrice, iterableService.getDescriptionsAndSortByPrice(basicVehicles))
        }

    }

    @Nested
    inner class GroupByBodyTypeTest {

        @Test
        fun `empty list remains unchanged`() {
            assertEquals(mapOf(), iterableService.groupByBodyType(listOf()))
        }

        @Test
        fun `all body types are distinct`() {
            val expected = mapOf(
                BodyType.Roadster to listOf(basicVehicles[0]),
                BodyType.Sedan to listOf(basicVehicles[1]),
                BodyType.Minivan to listOf(basicVehicles[2]),
                BodyType.Crossover to listOf(basicVehicles[3])
            )
            assertEquals(expected, iterableService.groupByBodyType(basicVehicles))
        }

        @Test
        fun `a few vehicles of same body type`() {
            val sedans = listOf(
                Vehicle("a", BodyType.Sedan, (0..100).random()),
                Vehicle("b", BodyType.Sedan, (0..100).random()),
                Vehicle("c", BodyType.Sedan, (0..100).random())
            )
            assertEquals(mapOf(BodyType.Sedan to sedans), iterableService.groupByBodyType(sedans))
        }

    }

    @Nested
    inner class FilterAndTakeThreeTest {

        @Test
        fun `empty list remains unchanged`() {
            assertEquals(listOf(), iterableService.filterAndTakeThree(listOf()) { true })
        }

        @Test
        fun `list of size less than 3 with always true predicate`() {
            assertEquals(listOf(basicVehiclesWithDescription[0].description, basicVehiclesWithDescription[1].description),
                iterableService.filterAndTakeThree(
                    listOf(basicVehiclesWithDescription[0].vehicle, basicVehiclesWithDescription[1].vehicle)
                ) { true }
            )
        }

        @Test
        fun `list of size greater than 3 with always true predicate`() {
            assertEquals(basicDescriptions.take(3), iterableService.filterAndTakeThree(basicVehicles) { true })
        }

        @Test
        fun `always false predicate returns empty list`() {
            assertEquals(listOf(), iterableService.filterAndTakeThree(basicVehicles) { false })
        }

        @Test
        fun `some custom predicate`() {
            val expected = listOf(
                basicDescriptions[0],
                basicDescriptions[2],
                basicDescriptions[3]
            )
            assertEquals(expected, iterableService.filterAndTakeThree(basicVehicles) { it.priceInRubles > 30 })
        }
    }

}
