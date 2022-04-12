package tinkoff.repository

import org.springframework.context.annotation.Primary
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import tinkoff.model.Citizen
import tinkoff.model.CitizenRepository
import java.math.BigInteger
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Primary
@Service
class JpaCitizenRepositoryImplementation(private val repository: JpaCitizenRepository) : CitizenRepository {

    override fun addCitizen(citizen: Citizen) {
        repository.save(citizen)
    }

    override fun getCitizen(id: Int): Citizen? {
         return repository.findById(id).unwrap()
    }

    override fun getPageOfCitizens(pageNumber: Int, pageSize: Int): List<Citizen> {
        return repository.findAll(PageRequest.of(pageNumber, pageSize)).toList()
    }

    override fun clear() {
        repository.deleteAll()
    }

    private fun <T> Optional<T>.unwrap(): T? = orElse(null)

}

@Table(name = "citizens")
@Entity
data class CitizenDB(
    @Id
    @Column(name = "id")
    val id: BigInteger,
    @Column(name = "personal_id")
    val personalId: Int,
    @Column(name = "crime_history")
    val crimeHistory: String
)