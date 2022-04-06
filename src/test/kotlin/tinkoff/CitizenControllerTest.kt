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
import org.springframework.test.web.servlet.ResultActions
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
            performPost(personalId)
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(citizen)))
        }

        @Test
        fun `post citizen with bad id`() {
            performPost(-1)
                .andExpect(status().is4xxClientError)
        }

        @Test
        fun `post citizen when FBI client doesn't response`() {
            setUpBrokenFBIClient()
            performPost(123)
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
            performPost(personalId)
            performGet(personalId)
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(citizen)))
        }

        @Test
        fun `get non-existent citizen`() {
            performGet(123)
                .andExpect(status().is4xxClientError)
        }

        @Test
        fun `get with an exception from repository`() {
            every { citizenRepository.getCitizen(any()) } throws Exception()
            performGet(123)
                .andExpect(status().is5xxServerError)
        }
    }

    @Nested
    inner class GetPageTest {

        private val ids = (1..4).toList()

        private val citizens = ids.map { Citizen(it, fbiClientResponse) }

        @BeforeEach
        fun addSomeCitizens() {
            ids.forEach { performPost(it) }
        }

        @Test
        fun `get first page`() {
            performGetPage(1, 2)
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(citizens.take(2))))
        }

        @Test
        fun `get second page`() {
            performGetPage(2, 2)
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(citizens.subList(2, 4))))
        }

        @Test
        fun `get partial page`() {
            performGetPage(2, 3)
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(citizens.subList(3, 4))))
        }

        @Test
        fun `get non-existent page`() {
            performGetPage(5, 1)
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(listOf<Citizen>())))
        }

        @Test
        fun `get page with negative number`() {
            performGetPage(-1, 4)
                .andExpect(status().is4xxClientError)
        }

        @Test
        fun `get page with zero size`() {
            performGetPage(2, 0)
                .andExpect(status().is4xxClientError)
        }
    }

    private fun performPost(personalId: Int): ResultActions = mockMvc.perform(
        post("/citizen/verify")
            .param("personalId", "$personalId")
    )

    private fun performGet(personalId: Int): ResultActions = mockMvc.perform(
        get("/citizen/get/{id}", 123)
    )

    private fun performGetPage(pageNumber: Int, pageSize: Int): ResultActions = mockMvc.perform(
        get("/citizen/page")
            .param("page", "$pageNumber")
            .param("size", "$pageSize")
    )

}
