package tinkoff.repository

import org.springframework.stereotype.Service
import tinkoff.model.Citizen
import tinkoff.model.CitizenRepository

@Service
class InMemoryCitizenRepository : CitizenRepository {

    private val citizens = mutableMapOf<Int, Citizen>()

    override fun addCitizen(citizen: Citizen) {
        citizens[citizen.personalId] = citizen
    }

    override fun getCitizen(id: Int): Citizen? {
        return citizens[id]
    }

    override fun getPageOfCitizens(pageNumber: Int, pageSize: Int): List<Citizen> {
        //require(pageSize > 0) { "Page size must be positive!" }
        return citizens
            .values
            .toList()
            .subList(
                minOf((pageNumber - 1) * pageSize, citizens.size),
                minOf(pageNumber * pageSize, citizens.size)
            )
    }

}