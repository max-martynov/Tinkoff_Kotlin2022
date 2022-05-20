package tinkoff.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration {

    private val baseURL = "https://api.twitter.com/2"
    private val bearerToken =
        "AAAAAAAAAAAAAAAAAAAAAHkrcwEAAAAAfaYCb3wuqk3rbSp1ZWOZdYOozec%3DUTnFu8FqL59XxJsFQe853HXt58o5XG1ez4LE86KgNzDk4d0G2v"

    @Bean
    fun webClient(): WebClient = WebClient.builder()
        .baseUrl(baseURL)
        .defaultHeaders { it.setBearerAuth(bearerToken) }
        .build()
}