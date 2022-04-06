package tinkoff.model

interface CitizenRepository {

    fun addCitizen(citizen: Citizen): Boolean

    fun getCitizen(id: Int): Citizen?

    fun getPageOfCitizens(pageNumber: Int, pageSize: Int): List<Citizen>

}
