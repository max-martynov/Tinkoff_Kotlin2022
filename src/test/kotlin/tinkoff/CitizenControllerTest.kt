package tinkoff

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import tinkoff.model.Citizen
import tinkoff.model.UnverifiedCitizen
import tinkoff.service.CrimeServiceClient


@SpringBootTest
@AutoConfigureMockMvc
class CitizenControllerTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) {


    @MockkBean
    private val reliableCrimeServiceClient: CrimeServiceClient = mockk()

    init {
        every { reliableCrimeServiceClient.getCrimeHistory(any()) } returns "ok"
    }

    @Nested
    inner class VerifyEndpointTest {

        @Test
        fun `gg wp`() {
            doTest(
                UnverifiedCitizen(1, "max"),
                Citizen(1, "max", "ok")
            )
        }

        private fun <Request, Response> doTest(input: Request, expectation: Response) {
            mockMvc.post("/mockmvc/validate") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(input)
                accept = MediaType.APPLICATION_JSON
            }.andExpect {
                content { contentType(MediaType.APPLICATION_JSON) }
                content { json(objectMapper.writeValueAsString(expectation)) }
            }
        }
    }


}