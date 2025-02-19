package com.example.retailer.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration

class RabbitConfig {

    @Bean
    fun queue(): Queue = Queue("retailer_queue")

    @Bean
    fun topic(): TopicExchange = TopicExchange("distributor_exchange")

    @Bean
    fun binding(queue: Queue?, topic: TopicExchange?): Binding = BindingBuilder
        .bind(queue)
        .to(topic)
        .with("retailer.ctacshamsheev.#")

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate? =
        RabbitTemplate(connectionFactory).apply {
            messageConverter = messageConverter()
            setExchange(topic().name)
        }

    @Bean
    fun messageConverter(): MessageConverter =
        Jackson2JsonMessageConverter(jacksonObjectMapper())
}