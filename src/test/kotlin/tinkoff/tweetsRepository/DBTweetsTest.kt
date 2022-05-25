package tinkoff.tweetsRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tinkoff.repository.tweets.db.DBTweetsRepository

@SpringBootTest
class DBTweetsTest(@Autowired override val tweetsRepository: DBTweetsRepository) : TweetsRepositoryTest()