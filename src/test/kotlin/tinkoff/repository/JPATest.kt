package tinkoff.repository

import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tinkoff.model.CitizenRepository

@SpringBootTest
class JPATest(@Autowired override val repository: JpaCitizenRepositoryImplementation) : RepositoryTest()