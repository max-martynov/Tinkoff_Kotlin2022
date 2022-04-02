package tinkoff.service

import org.springframework.stereotype.Service
import tinkoff.model.Citizen
import tinkoff.model.CitizensRepository

@Service
class InMemoryUserRepository : CitizensRepository {

    private val users = mutableSetOf<Citizen>()

    override fun addUser(newUser: Citizen) {
        users.add(newUser)
    }

    override fun getUser(login: String): Citizen? {
        return users.find { it.login == login }
    }
}