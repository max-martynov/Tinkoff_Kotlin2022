package tinkoff.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder


@Service
class FBIClient(
    private val restTemplate: RestTemplate,
    @Value("\${fbi.address}") private val fbiAddress: String
) {

    fun getCrimeHistory(personalId: Int): String = (0..1000).random().toString()
//        restTemplate.getForObject(
//            UriComponentsBuilder
//                .fromHttpUrl(fbiAddress)
//                .path("/$personalId")
//                .build()
//                .toUriString()
//        )
}
