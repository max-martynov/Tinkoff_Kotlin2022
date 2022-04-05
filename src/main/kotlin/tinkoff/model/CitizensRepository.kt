package tinkoff.model

interface CitizensRepository {

    fun addCitizen(citizen: Citizen)

    fun getCitizen(id: Int): Citizen?

    fun getPageOfCitizens(pageNumber: Int, pageSize: Int): List<Citizen> {
        require(pageSize > 0) { "Page size must be positive!" }
        val allCitizens = getAll()
        return allCitizens.subList(
            minOf((pageNumber - 1) * pageSize, allCitizens.size),
            minOf(pageNumber * pageSize, allCitizens.size)
        )
    }

    fun getAll(): List<Citizen>

}