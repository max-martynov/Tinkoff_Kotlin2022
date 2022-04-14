package tinkoff.repository

import org.junit.jupiter.api.*
import tinkoff.model.Citizen
import tinkoff.model.CitizenRepository
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class RepositoryTest {

    abstract val repository: CitizenRepository

    @Nested
    inner class SingleCitizen {

        @Test
        fun `add and get normal citizen`() {
            val citizen = Citizen(1, "nothing")
            repository.addCitizen(citizen)
            assertEquals(citizen, repository.getCitizen(citizen.personalId))
        }

        @Test
        fun `add and get citizen with empty crime history works fine`() {
            val citizen = Citizen(2, "")
            repository.addCitizen(citizen)
            assertEquals(citizen, repository.getCitizen(citizen.personalId))
        }

        @Test
        fun `trying to get non-existent citizen should return null`() {
            assertNull(repository.getCitizen(3))
        }
    }

    @Nested
    inner class MultipleCitizens {

        private val size = 5

        private val citizens = List(size) { createRandomCitizen() }.apply {
            repository.clear()
            this.forEach(repository::addCitizen)
        }

        @Test
        fun `get one by id`() {
            citizens.forEach { assertEquals(it, repository.getCitizen(it.personalId)) }
        }

        @Test
        fun `get normal page`() {
            assertEquals(citizens.take(3), repository.getPageOfCitizens(0, 3))
        }

        @Test
        fun `get partial page`() {
            assertEquals(citizens.subList(3, 5), repository.getPageOfCitizens(1, 3))
        }

        @Test
        fun `get empty page`() {
            assertEquals(listOf(), repository.getPageOfCitizens(2, 3))
        }
    }

    private fun createRandomCitizen(): Citizen =
        Citizen((1..1000).random(), (1..3).map { ('a'..'z').random() }.joinToString(""))
}