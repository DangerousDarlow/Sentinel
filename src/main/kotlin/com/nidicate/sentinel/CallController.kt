package com.nidicate.sentinel

import com.nexmo.client.NexmoClient
import com.nexmo.client.auth.JWTAuthMethod
import com.nidicate.sentinel.model.Call
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.FileSystems
import com.nexmo.client.voice.Call as NexmoCall

@RestController
@RequestMapping("call")
class CallController(
        val nexmoConfig: NexmoConfig
) {

    @PostMapping("create")
    fun create(@RequestBody call: Call) {
        println(call)

        val path = FileSystems.getDefault().getPath(nexmoConfig.privateKeyFilePath)

        val auth = JWTAuthMethod(
                nexmoConfig.applicationId,
                path
        )

        val nexmoClient = NexmoClient(auth)

        nexmoClient.voiceClient.createCall(NexmoCall(call.numberToCall, "123", "https://developer.nexmo.com/ncco/tts.json"))
    }
}