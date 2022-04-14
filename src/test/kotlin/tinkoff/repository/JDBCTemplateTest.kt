package tinkoff.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JDBCTemplateTest(@Autowired override val repository: JdbcCitizenRepository) : RepositoryTest()