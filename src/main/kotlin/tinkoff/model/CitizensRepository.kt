package tinkoff.model

interface CitizensRepository {

    fun addCitizen(citizen: Citizen)

    fun getCitizen(id: Int): Citizen?
}