package tinkoff.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration {

    private val baseURL = "https://twitter.com"

    @Bean
    fun webClient(): WebClient = WebClient.builder()
        .baseUrl(baseURL)
        .build()
}