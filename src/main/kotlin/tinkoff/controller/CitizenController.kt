package tinkoff.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tinkoff.model.Citizen
import tinkoff.service.CitizenService

@RestController
@RequestMapping("/citizen")
class CitizenController(
    private val citizenService: CitizenService
) {

    @PostMapping("/verify")
    fun verifyCitizen(@RequestParam personalId: Int): ResponseEntity<Citizen> {
        return citizenService.verifyCitizen(personalId)
    }

    @GetMapping("/get/{id}")
    fun getUser(@PathVariable id: Int): ResponseEntity<Citizen> {
        return citizenService.getCitizen(id)
    }

    @GetMapping("/page")
    fun getPage(@RequestParam page: Int, @RequestParam size: Int): ResponseEntity<List<Citizen>> {
        return citizenService.getPage(page, size)
    }

}