import util.Constants


data class Vehicle(
    val model: String,
    val bodyType: BodyType,
    val priceInRubles: Int,
) {

    fun getPriceInDollars(): Int = priceInRubles * Constants.EXCHANGE_RATE

    fun getDescription(): String = "$bodyType $model costs ${getPriceInDollars()}$"

}
