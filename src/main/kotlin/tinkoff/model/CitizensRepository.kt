package tinkoff.model

interface CitizensRepository {

    fun addCitizen(citizen: Citizen)

    fun getCitizen(id: Int): Citizen?

    fun getPageOfCitizens(pageNumber: Int, pageSize: Int): List<Citizen> {
        val allCitizens = getAll()
        return allCitizens.subList(
            minOf((pageNumber - 1) * pageSize, allCitizens.size),
            minOf(pageNumber * pageSize, allCitizens.size)
        )
    }

    fun getAll(): List<Citizen>

}