package com.nidicate.sentinel

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("nexmo")
class NexmoConfig {
    lateinit var applicationId: String
    lateinit var apiKey: String
    lateinit var privateKeyFilePath: String
}