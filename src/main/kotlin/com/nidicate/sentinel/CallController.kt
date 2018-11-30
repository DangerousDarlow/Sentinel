package com.nidicate.sentinel

import com.nexmo.client.NexmoClient
import com.nexmo.client.voice.ModifyCallAction
import com.nidicate.sentinel.model.Call
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import com.nexmo.client.voice.Call as NexmoCall

@RestController
@RequestMapping("call")
class CallController(
        val nexmoClient: NexmoClient,
        val dodgyRepository: DodgyRepository
) {

    @PostMapping("create")
    fun create(@RequestBody call: Call, servlet: HttpServletRequest): Call {
        val outboundUrl = "${servlet.scheme}://${servlet.serverName}/nexmo/outbound"

        val event = nexmoClient.voiceClient.createCall(
                NexmoCall(call.numberToCall, "123", outboundUrl)
        )

        dodgyRepository.callUuid = event.uuid

        return call.copy(uuid = event.uuid)
    }

    @DeleteMapping("hangup/{uuid}")
    fun hangup(@PathVariable uuid: String) {
        nexmoClient.voiceClient.modifyCall(uuid, ModifyCallAction.HANGUP)
    }
}