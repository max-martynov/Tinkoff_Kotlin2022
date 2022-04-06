package tinkoff.service

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tinkoff.model.Citizen

@RestController
@RequestMapping("/citizen")
class CitizenController(
    private val citizenVerifier: CitizenVerifier
) {

    @PostMapping("/verify")
    fun verifyCitizen(@RequestParam personalId: Int): ResponseEntity<Citizen> {
        return citizenVerifier.verifyCitizen(personalId)
    }

    @GetMapping("/get/{id}")
    fun getUser(@PathVariable id: Int): ResponseEntity<Citizen> {
        return citizenVerifier.getCitizen(id)
    }

    @GetMapping("/page")
    fun getPage(@RequestParam page: Int, @RequestParam size: Int): ResponseEntity<List<Citizen>> {
        return citizenVerifier.getPage(page, size)
    }

}