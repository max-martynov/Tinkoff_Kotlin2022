package tinkoff.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder

@Service
class CrimeServiceClient(
    private val restTemplate: RestTemplate,
    @Value("\${crime.service.address}") private val crimeServiceAddress: String
) {

    fun getCrimeHistory(personalIdNumber: Int): String? = try {
        restTemplate.getForObject(
            UriComponentsBuilder
                .fromHttpUrl(crimeServiceAddress)
                .path("/$personalIdNumber")
                .build()
                .toUriString()
        )
    } catch (e: RestClientException) {
        null
    }

}
