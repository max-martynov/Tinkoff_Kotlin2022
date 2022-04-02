package tinkoff.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tinkoff.model.Citizen
import tinkoff.model.CitizensRepository

@RestController
@RequestMapping("/user")
class UserController(
    private val userRepository: CitizensRepository
) {

    @PostMapping("")
    fun addNewUser(@RequestBody user: Citizen) {
        userRepository.addUser(user)
    }

    @GetMapping("")
    fun getUser(@RequestParam login: String): Citizen? {
        return userRepository.getUser(login)
    }

}