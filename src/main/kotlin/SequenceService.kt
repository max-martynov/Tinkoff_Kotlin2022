class SequenceService {

    private val exchangeRate = (0..100).random()

    fun getDescriptionsAndSortByPrice(vehicles: Sequence<Vehicle>): Sequence<String> =
        vehicles.sortedBy { it.getPriceInDollars(exchangeRate) }.map { it.getDescription(exchangeRate) }

    fun groupByBodyType(vehicles: Sequence<Vehicle>): Map<BodyType, List<Vehicle>> = vehicles.groupBy { it.bodyType }

    fun filterAndTakeThree(vehicles: Sequence<Vehicle>, predicate: (Vehicle) -> Boolean): Sequence<String> =
        vehicles.filter(predicate).map { it.getDescription(exchangeRate) }.take(3)
}