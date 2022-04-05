package tinkoff.model

data class UnverifiedCitizen(
    val personalIdNumber: Int,
    val name: String,
) {
    fun toCitizen(crimeHistory: String) =
        Citizen(personalIdNumber, name, crimeHistory)
}
