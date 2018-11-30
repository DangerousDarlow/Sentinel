package com.nidicate.sentinel

import org.springframework.stereotype.Component

// Dodgy because this isn't thread safe .. but hey this is a spike
@Component
class DodgyRepository {
    var callUuid: String? = null
}