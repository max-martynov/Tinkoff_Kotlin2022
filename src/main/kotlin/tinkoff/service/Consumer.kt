package tinkoff.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import tinkoff.model.*
import tinkoff.model.processor.EmailProcessor
import tinkoff.model.processor.EventProcessor
import tinkoff.model.processor.PushProcessor
import tinkoff.model.processor.SMSProcessor
import tinkoff.repository.EventsRepository

@Component
class Consumer(val eventsRepository: EventsRepository) {

    private val mapper = ObjectMapper().registerKotlinModule()


    @JmsListener(destination = "\${spring.activemq.queue-name}")
    fun consume(message: String) {
        try {
            val event = mapper.readValue(message, Event::class.java)!!
            val processor = event.type.eventProcessor
            if (processor.process(event)) {
                eventsRepository.updateStatus(event.id, EventStatus.DONE)
            } else {
                eventsRepository.updateStatus(event.id, EventStatus.ERROR)
                throw Exception("Error while processing $event.")
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

}