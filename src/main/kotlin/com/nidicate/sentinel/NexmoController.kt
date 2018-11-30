package com.nidicate.sentinel

import com.nexmo.client.NexmoClient
import com.nexmo.client.incoming.InputEvent
import com.nexmo.client.voice.ncco.InputAction
import com.nexmo.client.voice.ncco.Ncco
import com.nexmo.client.voice.ncco.TalkAction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("nexmo")
class NexmoController(
        val nexmoClient: NexmoClient,
        val dodgyRepository: DodgyRepository
) {

    // Controller request mapping value extracted using reflection
    val controllerMapping by lazy { NexmoController::class.java.getAnnotation(RequestMapping::class.java).value[0] }

    @PostMapping("event")
    fun event(@RequestBody request: Map<String, Any?>) {
        print("event status: ${request["status"]}")
        print("        full: $request")

        val callUuid = request["uuid"] as String
        if (callUuid != dodgyRepository.callUuid) {
            return
        }

        if (request["status"] == "answered") {
            // Launch Kotlin co-routine
            GlobalScope.launch {
                delay(2000)
                print("Sending DTMF for $callUuid")
                nexmoClient.voiceClient.sendDtmf(callUuid, "159")
            }
        }
    }

    @PostMapping("answer")
    fun answer(@RequestBody request: Map<String, Any?>, servlet: HttpServletRequest): String {
        print("answer: $request")

        val talkAction = TalkAction
                .Builder("Hi. You have called a New Voice Media automated test application. Please hangup.")
                .loop(3)
                .bargeIn(true)
                .build()

        val dtmfUrl = "${servlet.scheme}://${servlet.serverName}/$controllerMapping/dtmf"

        val inputAction = InputAction.Builder()
                .eventUrl(dtmfUrl)
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

    @GetMapping("outbound")
    fun outbound(): String {
        val talkAction = TalkAction
                .Builder("Hi. This is a New Voice Media automated test application. Please hangup.")
                .loop(3)
                .build()

        return Ncco(talkAction).toJson()
    }

    private fun print(message: String) {
        println("${LocalDateTime.now()} $message")
    }
}