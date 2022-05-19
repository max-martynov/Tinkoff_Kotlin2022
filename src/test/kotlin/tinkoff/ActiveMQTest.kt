package tinkoff

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tinkoff.model.Event
import tinkoff.model.EventStatus
import tinkoff.model.EventType
import tinkoff.repository.EventsRepository
import java.lang.Thread.sleep

import kotlin.test.Test
import kotlin.test.assertEquals


@SpringBootTest
class ActiveMQTest(@Autowired private val eventsRepository: EventsRepository) {

    @Test
    fun `test good scenario, ie new event will be sent`() {
        sleep(2000L)
        val event = Event(1, EventType.SMS, "hello!", EventStatus.NEW)
        eventsRepository.addEvent(event)
        sleep(22000L)
        assertEquals(EventStatus.DONE, eventsRepository.getEvent(event.id)?.status)
    }

    @Test
    fun `test bad scenario, ie errored event won't be sent`() {
        sleep(2000L)
        val event = Event(2, EventType.SMS, "hi!", EventStatus.ERROR)
        eventsRepository.addEvent(event)
        sleep(22000L)
        assertEquals(EventStatus.ERROR, eventsRepository.getEvent(event.id)?.status)
    }

}