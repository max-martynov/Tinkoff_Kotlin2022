class IterableService {

    private val exchangeRate = (0..100).random()

    fun getDescriptionsAndSortByPrice(vehicles: Iterable<Vehicle>): Iterable<String> =
        vehicles.sortedBy { it.getPriceInDollars(exchangeRate) }.map { it.getDescription(exchangeRate) }

    fun groupByBodyType(vehicles: Iterable<Vehicle>): Map<BodyType, List<Vehicle>> = vehicles.groupBy { it.bodyType }

    fun filterAndTakeThree(vehicles: Iterable<Vehicle>, predicate: (Vehicle) -> Boolean): Iterable<String> =
        vehicles.filter(predicate).map { it.getDescription(exchangeRate) }.take(3)
}
