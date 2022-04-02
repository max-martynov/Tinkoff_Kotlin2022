package tinkoff.model

interface UserRepository {

    fun addUser(newUser: User)

    fun getUser(login: String): User?
}