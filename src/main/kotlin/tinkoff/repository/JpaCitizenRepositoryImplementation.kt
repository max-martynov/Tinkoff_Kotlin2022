package tinkoff.repository

import org.springframework.context.annotation.Primary
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import tinkoff.model.Citizen
import tinkoff.model.CitizenRepository
import java.math.BigInteger
import java.util.*
import javax.persistence.*

@Service
class JpaCitizenRepositoryImplementation(private val repository: JpaCitizenRepository) : CitizenRepository {

    override fun addCitizen(citizen: Citizen) {
        repository.save(CitizenDB(personalId = citizen.personalId, crimeHistory = citizen.crimeHistory))
    }

    override fun getCitizen(id: Int): Citizen? {
        val citizenDB = repository.findFirstByPersonalIdEquals(id) ?: return null
        return citizenDB.toCitizen()
    }

    override fun getPageOfCitizens(pageNumber: Int, pageSize: Int): List<Citizen> {
        return repository.findAll(PageRequest.of(pageNumber, pageSize)).toList().map { it.toCitizen() }
    }

    override fun clear() {
        repository.deleteAll()
    }

}

