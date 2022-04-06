package tinkoff.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import tinkoff.model.Citizen
import tinkoff.model.CitizenRepository

@Service
class CitizenVerifier(
    val citizensRepository: CitizenRepository,
    val fbiClient: FBIClient
) {

    fun verifyCitizen(personalId: Int): ResponseEntity<Citizen> {
        val crimeHistory = fbiClient.getCrimeHistory(personalId)
            ?: throw IllegalArgumentException("FBI cannot find citizen with id=$personalId!")
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
        return ResponseEntity.ok(citizensRepository.getPageOfCitizens(pageNumber, pageSize))
    }

}