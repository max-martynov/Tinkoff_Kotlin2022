package tinkoff.configuration

import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.jms.core.JmsTemplate
import javax.jms.ConnectionFactory


class ActiveMQConfig {

    @Value("\${activemq.broker-url}")
    lateinit var brokerUrl: String

    @Bean
    fun connectionFactory(): ConnectionFactory {
        val activeMQConnectionFactory = ActiveMQConnectionFactory()
        activeMQConnectionFactory.brokerURL = brokerUrl
        return activeMQConnectionFactory
    }

    @Bean
    fun jmsTemplate(connectionFactory: ConnectionFactory): JmsTemplate {
        val jmsTemplate = JmsTemplate()
        jmsTemplate.connectionFactory = connectionFactory
        return jmsTemplate
    }
}