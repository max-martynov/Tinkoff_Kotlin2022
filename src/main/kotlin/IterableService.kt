class IterableService {

    fun getDescriptionsAndSortByPrice(vehicles: Iterable<Vehicle>): Iterable<String> =
        vehicles.sortedBy { it.getPriceInDollars() }.map { it.getDescription() }

    fun groupByBodyType(vehicles: Iterable<Vehicle>): Map<BodyType, List<Vehicle>> = vehicles.groupBy { it.bodyType }

    fun filterAndTakeThree(vehicles: Iterable<Vehicle>, predicate: (Vehicle) -> Boolean): Iterable<String> =
        vehicles.filter(predicate).map { it.getDescription() }.take(3)
}
