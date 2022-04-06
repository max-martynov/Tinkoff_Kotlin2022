package tinkoff

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeAll
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
    lateinit var workingFBIClient: FBIClient

    @MockkBean
    lateinit var brokenFBIClient: FBIClient

    val workingFBIClientResponse = "ok"

    @BeforeAll
    fun setUp() {
        every { workingFBIClient.getCrimeHistory(any()) } returns workingFBIClientResponse
    }

    @Nested
    inner class PostTest {

        @Test
        fun `post citizen with workingFBIClient`() {
            val personalId = 123
            val citizen = Citizen(personalId, workingFBIClientResponse)
            mockMvc.perform(
                post("/citizen/verify")
                    .param("personalId", "123")
            )
                .andExpect(status().isOk)
                .andExpect(content().json(mapper.writeValueAsString(citizen)))
        }

        @Test
        fun `post citizen with `

//        private fun <Request, Response> doTest(input: Request, expectation: Response) {
//            mockMvc.post("/mockmvc/validate") {
//                contentType = MediaType.APPLICATION_JSON
//                content = mapper.writeValueAsString(input)
//                accept = MediaType.APPLICATION_JSON
//            }.andExpect {
//                content { contentType(MediaType.APPLICATION_JSON) }
//                content { json(mapper.writeValueAsString(expectation)) }
//            }
//        }
    }


}
