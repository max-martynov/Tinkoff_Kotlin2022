package tinkoff.model

import tinkoff.model.processor.EmailProcessor
import tinkoff.model.processor.EventProcessor
import tinkoff.model.processor.PushProcessor
import tinkoff.model.processor.SMSProcessor

enum class EventType(val eventProcessor: EventProcessor) {
    SMS(SMSProcessor),
    EMAIL(EmailProcessor),
    PUSH(PushProcessor)
}
