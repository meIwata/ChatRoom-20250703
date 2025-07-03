package com.example

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*

fun Application.configureRouting() {
    routing {
        // 靜態檔案路由，讓 /static/ 下的檔案可被存取
        static("/static") {
            resources("static")
        }
        // 根目錄導向 chat.html
        get("/") {
            call.respondRedirect("/static/chat.html")
        }
    }
}
