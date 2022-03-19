data class Vehicle(
    val brand: String,
    val model: String,
    val bodyType: BodyType,
    val priceInRubles: Int,
    val gasMileage: Int
) {

    fun getPriceInDollars(exchangeRate: Int): Int = priceInRubles * exchangeRate

    fun getDescription(exchangeRate: Int): String =
        """
            Vehicle information for $brand $model
            - Body type: $bodyType
            - Price in dollars: ${getPriceInDollars(exchangeRate)}
            - Gas mileage: $gasMileage
        """.trimIndent()

}
