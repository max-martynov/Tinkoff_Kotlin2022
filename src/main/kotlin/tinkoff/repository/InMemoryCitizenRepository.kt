package tinkoff.repository

import org.springframework.stereotype.Service
import tinkoff.model.Citizen
import tinkoff.model.CitizensRepository

@Service
class InMemoryCitizenRepository : CitizensRepository {

    private val citizens = mutableMapOf<Int, Citizen>()

    override fun addCitizen(citizen: Citizen) {
        citizens[citizen.id] = citizen
    }

    override fun getCitizen(id: Int): Citizen? {
        return citizens[id]
    }
}