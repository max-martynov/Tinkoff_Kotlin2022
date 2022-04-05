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

    fun verifyCitizen(unverifiedCitizen: UnverifiedCitizen): Citizen? {
        val crimeHistory = crimeServiceClient.getCrimeHistory(unverifiedCitizen.personalIdNumber)
        val citizen = crimeHistory?.let { unverifiedCitizen.toCitizen(it) } ?: return null
        citizensRepository.addCitizen(citizen)
        return citizen
    }

    fun getCitizen(id: Int): Citizen? = citizensRepository.getCitizen(id)

}