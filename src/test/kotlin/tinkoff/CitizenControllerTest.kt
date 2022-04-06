package tinkoff

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tinkoff.model.Citizen
import tinkoff.model.CitizenRepository
import tinkoff.service.FBIClient

@SpringBootTest
@AutoConfigureMockMvc
class CitizenControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val mapper: ObjectMapper
) {

    @SpykBean
    lateinit var citizenRepository: CitizenRepository

    @MockkBean
    lateinit var fbiClient: FBIClient

    val fbiClientResponse = "ok"

    @BeforeEach
    fun setUp() {
        every { fbiClient.getCrimeHistory(any()) } returns fbiClientResponse
        citizenRepository.clear()
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

    @Nested
    inner class GetTest {

        @Test
        fun `get normal existing citizen`() {
            val personalId = 123
            val citizen = Citizen(personalId, fbiClientResponse)
            mockMvc.perform(
                post("/citizen/verify")
                    .param("personalId", "$personalId")
            )
            mockMvc.perform(
                get("/citizen/get/{id}", personalId)
            )
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(citizen)))
        }

        @Test
        fun `get non-existent citizen`() {
            mockMvc.perform(
                get("/citizen/get/{id}", 123)
            )
                .andExpect(status().is4xxClientError)
        }

        @Test
        fun `get with an exception from repository`() {
            every { citizenRepository.getCitizen(any()) } throws Exception()
            mockMvc.perform(
                get("/citizen/get/{id}", 123)
            )
                .andExpect(status().is5xxServerError)
        }
    }




}
