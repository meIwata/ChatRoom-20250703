package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.Collections
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.time.Duration.Companion.seconds

val connections = CopyOnWriteArrayList<DefaultWebSocketServerSession>()

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(WebSockets) {
        pingPeriod = 15.seconds
    }
    routing {
        webSocket("/chat") {
            send("請輸入暱稱：")
            val nickname = (incoming.receive() as? Frame.Text)?.readText() ?: return@webSocket

            // 通知所有人有新使用者加入
            connections.forEach { it.send("[$nickname] 進入聊天室") }
            connections.add(this)
            try {
                send("歡迎 $nickname 加入聊天室！")
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        // 廣播給所有連線者
                        connections.forEach { it.send("[$nickname] $text") }
                    }
                }
            } finally {
                connections.remove(this)
                connections.forEach { it.send("[$nickname] 離開聊天室") }
            }
        }
    }
}