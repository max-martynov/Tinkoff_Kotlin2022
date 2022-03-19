class SequenceService {

    fun getDescriptionsAndSortByPrice(vehicles: Sequence<Vehicle>): Sequence<String> =
        vehicles.sortedBy { it.getPriceInDollars() }.map { it.getDescription() }

    fun groupByBodyType(vehicles: Sequence<Vehicle>): Map<BodyType, List<Vehicle>> = vehicles.groupBy { it.bodyType }

    fun filterAndTakeThree(vehicles: Sequence<Vehicle>, predicate: (Vehicle) -> Boolean): Sequence<String> =
        vehicles.filter(predicate).map { it.getDescription() }.take(3)
}