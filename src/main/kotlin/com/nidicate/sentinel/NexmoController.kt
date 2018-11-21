package com.nidicate.sentinel

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("nexmo")
class NexmoController {

    @PostMapping("event")
    fun event(@RequestBody request: Map<String, Any?>) {
        println(request)
    }

    @PostMapping("answer")
    fun answer(@RequestBody request: Map<String, Any?>) {
        println(request)
    }
}