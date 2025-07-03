package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
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
    configureRouting() // 加入這行，啟用 Routing.kt 的路由設定
    routing {
        webSocket("/chat") {
            send("請輸入暱稱：")
            val nickname = (incoming.receive() as? Frame.Text)?.readText() ?: return@webSocket

            // 1. 先把歷史訊息發給新用戶
            ChatHistory.getRecent().forEach { msg ->
                send(msg)
            }

            // 2. 通知所有人有新使用者加入，並加入歷史
            val joinMsg = "[$nickname] 進入聊天室"
            connections.forEach { it.send(joinMsg) }
            ChatHistory.add(joinMsg)

            connections.add(this)
            try {
                val welcomeMsg = "歡迎 $nickname 加入聊天室！"
                send(welcomeMsg)
                ChatHistory.add(welcomeMsg)
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        val chatMsg = "[$nickname] $text"
                        // 廣播給所有連線者
                        connections.forEach { it.send(chatMsg) }
                        // 加到歷史
                        ChatHistory.add(chatMsg)
                    }
                }
            } finally {
                connections.remove(this)
                val leaveMsg = "[$nickname] 離開聊天室"
                connections.forEach { it.send(leaveMsg) }
                ChatHistory.add(leaveMsg)
            }
        }
    }
}