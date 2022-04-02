package model

class InMemoryUserRepository : UserRepository {

    private val users = mutableSetOf<User>()

    override fun addUser(newUser: User) {
        users.add(newUser)
    }

    override fun getUser(login: String): User? {
        return users.find { it.login == login }
    }
}