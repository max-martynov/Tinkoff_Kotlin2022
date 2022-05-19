package tinkoff

import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jms.core.JmsTemplate
import tinkoff.model.Event
import tinkoff.model.EventStatus
import tinkoff.model.EventType
import tinkoff.repository.EventsRepository
import java.lang.Thread.sleep
import javax.jms.Connection

import javax.jms.ConnectionFactory
import javax.jms.Queue
import javax.jms.Session
import kotlin.test.Test
import kotlin.test.assertEquals


@SpringBootTest
class Test(@Autowired private val eventsRepository: EventsRepository) {

//    class ActiveMQConfigTest {
//
//        @Bean
//        @Primary
//        fun connectionFactory(): ConnectionFactory {
//            return ActiveMQConnectionFactory("vm://localhost?broker.persistent=false")
//        }
//
//    }


    @Test
    fun `test good scenario`() {
        sleep(2000L)
        val event = Event(1, EventType.SMS, "hello!", EventStatus.NEW)
        eventsRepository.addEvent(event)
        sleep(10000L)
        assertEquals(EventStatus.DONE, eventsRepository.getEvent(event.id)?.status)
    }


}