package com.nidicate.sentinel

import com.nexmo.client.NexmoClient
import com.nexmo.client.voice.ModifyCallAction
import com.nidicate.sentinel.model.Call
import org.springframework.web.bind.annotation.*
import com.nexmo.client.voice.Call as NexmoCall

@RestController
@RequestMapping("call")
class CallController(val nexmoClient: NexmoClient) {

    @PostMapping("create")
    fun create(@RequestBody call: Call): Call {
        val event = nexmoClient.voiceClient.createCall(
                NexmoCall(call.numberToCall, "123", "https://developer.nexmo.com/ncco/tts.json")
        )

        return call.copy(uuid = event.uuid)
    }

    @DeleteMapping("hangup/{uuid}")
    fun hangup(@PathVariable uuid: String) {
        nexmoClient.voiceClient.modifyCall(uuid, ModifyCallAction.HANGUP)
    }
}