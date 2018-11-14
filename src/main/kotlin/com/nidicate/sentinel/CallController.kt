package com.nidicate.sentinel

import com.nidicate.sentinel.model.Call
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("call")
class CallController {

    @PostMapping("create")
    fun create(@RequestBody call: Call) {
        println(call)
    }
}