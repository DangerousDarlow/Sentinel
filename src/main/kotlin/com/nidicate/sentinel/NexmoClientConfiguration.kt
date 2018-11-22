package com.nidicate.sentinel

import com.nexmo.client.NexmoClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NexmoClientConfiguration(val nexmoConfigurationProperties: NexmoConfigurationProperties) {

    @Bean
    fun nexmoClient(): NexmoClient = NexmoClient.Builder()
            .applicationId(nexmoConfigurationProperties.applicationId)
            .privateKeyPath(nexmoConfigurationProperties.privateKeyFilePath)
            .build()
}