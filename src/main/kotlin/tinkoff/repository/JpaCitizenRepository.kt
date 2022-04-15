package tinkoff.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tinkoff.model.Citizen
import tinkoff.model.CitizenRepository
import java.math.BigInteger
import javax.persistence.*

@Repository
interface JpaCitizenRepository : JpaRepository<CitizenDB, Int> {

    fun findFirstByPersonalIdEquals(personalId: Int): CitizenDB?

}

@Table(name = "citizens")
@Entity
data class CitizenDB(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,
    @Column(name = "personal_id")
    val personalId: Int,
    @Column(name = "crime_history")
    val crimeHistory: String
) {
    fun toCitizen(): Citizen =
        Citizen(personalId, crimeHistory)
}