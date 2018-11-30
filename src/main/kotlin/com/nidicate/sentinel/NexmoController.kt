package com.nidicate.sentinel

import com.nexmo.client.incoming.InputEvent
import com.nexmo.client.voice.ncco.InputAction
import com.nexmo.client.voice.ncco.Ncco
import com.nexmo.client.voice.ncco.TalkAction
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("nexmo")
class NexmoController {

    // Controller request mapping value extracted using reflection
    val controllerMapping by lazy { NexmoController::class.java.getAnnotation(RequestMapping::class.java).value[0] }

    @PostMapping("event")
    fun event(@RequestBody request: Map<String, Any?>) {
        print("event status: ${request["status"]}")
        print("        full: $request")
    }

    @PostMapping("answer")
    fun answer(@RequestBody request: Map<String, Any?>, servlet: HttpServletRequest): String {
        print("answer: $request")

        val talkAction = TalkAction
                .Builder("Hi. You have called a New Voice Media automated test application. Please hangup.")
                .loop(0)
                .bargeIn(true)
                .build()

        val dtmfUrl = "${servlet.scheme}://${servlet.serverName}/$controllerMapping/dtmf"

        val inputAction = InputAction.Builder()
                .eventUrl(dtmfUrl)
                .submitOnHash(true)
                .build()

        return Ncco(talkAction, inputAction).toJson()
    }

    @PostMapping("dtmf")
    fun dtmf(@RequestBody request: String) {
        val inputEvent = InputEvent.fromJson(request)
        if (inputEvent.dtmf == "159") {
            print("SUCCESS")
        } else {
            print("Incorrect DTMF")
        }
    }

    private fun print(message: String) {
        println("${LocalDateTime.now()} $message")
    }
}