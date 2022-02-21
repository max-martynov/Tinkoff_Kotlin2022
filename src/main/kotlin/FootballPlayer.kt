abstract class FootballPlayer(
    val name: String,
    var age: Int,
    val country: String
) {

    open fun calculateTransferCost(): Int {
        return 1000 * age * calculateUsefulness()
    }

    protected abstract fun calculateUsefulness(): Int

    fun prettyPrintTransferCost() {
        println("$name costs ${calculateTransferCost()}$.")
    }
}