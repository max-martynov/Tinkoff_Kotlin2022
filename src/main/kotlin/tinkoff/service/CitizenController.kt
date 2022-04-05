package tinkoff.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tinkoff.model.Citizen
import tinkoff.model.CitizensRepository
import tinkoff.model.UnverifiedCitizen

@RestController
@RequestMapping("/citizen")
class CitizenController(
    private val fbi: FBI
) {

    @PostMapping("/verify")
    fun verifyCitizen(@RequestBody unverifiedCitizen: UnverifiedCitizen): Citizen? {
        return fbi.verifyCitizen(unverifiedCitizen)
    }

    @GetMapping("/get/{id}")
    fun getUser(@PathVariable id: Int): Citizen? {
        return fbi.getCitizen(id)
    }

}