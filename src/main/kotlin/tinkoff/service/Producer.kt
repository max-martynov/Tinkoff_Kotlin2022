package tinkoff.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import tinkoff.model.EventStatus
import tinkoff.repository.EventsRepository
import javax.jms.JMSException
import javax.jms.Queue


@Component
class Producer(val eventsRepository: EventsRepository) {

    @Autowired
    lateinit var jmsTemplate: JmsTemplate

    @Value("\${spring.activemq.queue-name}")
    private lateinit var queueName: String

    val mapper = ObjectMapper()

    @Scheduled(cron = "\${spring.activemq.cron}")
    fun produce() {
        eventsRepository.getEventsWithNewStatus().forEach { event ->
            try {
                jmsTemplate.convertAndSend(
                    queueName,
                    mapper.writeValueAsString(event)
                )
                eventsRepository.updateStatus(event.id, EventStatus.IN_PROCESS)
            } catch (e: JMSException) {
                println(e.message)
                eventsRepository.updateStatus(event.id, EventStatus.ERROR)
            }
        }
    }
}