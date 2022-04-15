package tinkoff.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import tinkoff.model.Citizen
import tinkoff.model.CitizenRepository

@Service
class CitizenService(
    val citizensRepository: CitizenRepository,
    val fbiClient: FBIClient
) {

    fun verifyCitizen(personalId: Int): ResponseEntity<Citizen> {
        require(personalId > 0) { "Personal ID must be positive!" }
        val crimeHistory = fbiClient.getCrimeHistory(personalId)
        val citizen = Citizen(personalId, crimeHistory)
        citizensRepository.addCitizen(citizen)
        return ResponseEntity.ok(citizen)
    }

    fun getCitizen(id: Int): ResponseEntity<Citizen> {
        val citizen = citizensRepository.getCitizen(id)
            ?: throw IllegalArgumentException("No citizen with id=$id!")
        return ResponseEntity.ok(citizen)
    }

    fun getPage(pageNumber: Int, pageSize: Int): ResponseEntity<List<Citizen>> {
        require(pageSize > 0) { "Page size must be positive!" }
        require(pageNumber >= 0) { "Page number must be non-negative!" }
        return ResponseEntity.ok(citizensRepository.getPageOfCitizens(pageNumber, pageSize))
    }
}