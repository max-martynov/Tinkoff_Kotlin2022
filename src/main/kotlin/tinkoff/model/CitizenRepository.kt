package tinkoff.model

interface CitizenRepository {

    fun addCitizen(citizen: Citizen)

    fun getCitizen(id: Int): Citizen?

    /**
     * Returns citizens [from, to)  in order of inserting.
     * Indices are 0-based.
     */
    fun getRangeOfCitizens(from: Int, to: Int): List<Citizen>

    fun clear()

}
