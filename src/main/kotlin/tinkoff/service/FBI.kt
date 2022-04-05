package tinkoff.service

import org.springframework.stereotype.Service
import tinkoff.model.Citizen
import tinkoff.model.CitizensRepository
import tinkoff.model.UnverifiedCitizen
import java.util.*

@Service
class FBI(
    val citizensRepository: CitizensRepository,
    val crimeServiceClient: CrimeServiceClient
) {

    fun verifyCitizen(unverifiedCitizen: UnverifiedCitizen): Citizen {
        val crimeHistory = crimeServiceClient.getCrimeHistory(unverifiedCitizen.personalIdNumber)
        requireNotNull(crimeHistory) { "No crime history found for unverified citizen with id=${unverifiedCitizen.personalIdNumber}!" }
        val citizen = unverifiedCitizen.toCitizen(crimeHistory)
        citizensRepository.addCitizen(citizen)
        return citizen
    }

    fun getCitizen(id: Int): Citizen {
        return requireNotNull(citizensRepository.getCitizen(id)) { "No citizen with id=$id!"  }
    }

    fun getPage(pageNumber: Int, pageSize: Int): List<Citizen> =
        citizensRepository.getPageOfCitizens(pageNumber, pageSize)

}