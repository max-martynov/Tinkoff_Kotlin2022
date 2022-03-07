abstract class FootballPlayer(
    private val name: String,
    val age: Int,
    protected val country: String
) {

    open fun calculateTransferCost(): Int {
        return 1000 * age * calculateUsefulness()
    }

    protected abstract fun calculateUsefulness(): Int

    fun prettyPrintTransferCost() {
        println("$name costs ${calculateTransferCost()}$.")
    }
}
