package tinkoff.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tinkoff.model.ReceivedEvent
import tinkoff.repository.EventsRepository


@RestController
class RestController{

    @Autowired
    private lateinit var repository: EventsRepository

    @PostMapping("/new-event")
    fun receiveEvent(@RequestBody input: ReceivedEvent) {
        val event = input.toEvent()
        repository.addEvent(event)
    }

}