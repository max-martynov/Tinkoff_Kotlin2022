package tinkoff.likesRecordsRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tinkoff.model.TweetsRepository
import tinkoff.repository.likes.db.DBLikesRecordsRepository

@SpringBootTest
class DBLikesTest(
    @Autowired override val likesRecordsRepository: DBLikesRecordsRepository,
    @Autowired val tweetsRepository: TweetsRepository
) : LikesRecordsRepositoryTest(tweetsRepository)