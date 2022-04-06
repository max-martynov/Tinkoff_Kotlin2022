package tinkoff

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tinkoff.model.Citizen
import tinkoff.service.FBIClient

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CitizenControllerTest(@Autowired private val mockMvc: MockMvc, @Autowired private val mapper: ObjectMapper) {

    @MockkBean
    lateinit var fbiClient: FBIClient

    val fbiClientResponse = "ok"

    @BeforeEach
    fun setUp() {
        every { fbiClient.getCrimeHistory(any()) } returns fbiClientResponse
    }

    @Nested
    inner class PostTest {

        @Test
        fun `post normal citizen`() {
            val personalId = 123
            val citizen = Citizen(personalId, fbiClientResponse)
            mockMvc.perform(
                post("/citizen/verify")
                    .param("personalId", "$personalId")
            )
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(citizen)))
        }

        @Test
        fun `post citizen with bad id`() {
            val personalId = -1
            mockMvc.perform(
                post("/citizen/verify")
                    .param("personalId", "$personalId")
            )
                .andExpect(status().is4xxClientError)
        }

        @Test
        fun `post citizen when FBI client doesn't response`() {
            setUpBrokenFBIClient()
            mockMvc.perform(
                post("/citizen/verify")
                    .param("personalId", "123")
            )
                .andExpect(status().is5xxServerError)
        }

        private fun setUpBrokenFBIClient() {
            every { fbiClient.getCrimeHistory(any()) } throws Exception()
        }
    }


}
