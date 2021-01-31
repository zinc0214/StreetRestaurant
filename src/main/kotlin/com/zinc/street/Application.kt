package com.zinc.street

import com.zinc.street.routes.registerNicknameRoutes
import com.zinc.street.routes.registerRestaurantRoutes
import com.zinc.street.routes.registerUserRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(CallLogging)

    registerUserRoutes()
    registerNicknameRoutes()
    registerRestaurantRoutes()
}