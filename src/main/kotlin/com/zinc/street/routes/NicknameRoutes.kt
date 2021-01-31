package com.zinc.street.routes

import com.zinc.street.dto.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerNicknameRoutes() {
    routing {
        nicknameRouting()
    }
}

fun Route.nicknameRouting() {
    route("/nickname") {
        post {
            val checkName = call.receive<NicknameCheck>()
            if (nickNameList.none { it == checkName.nickname }) {
                call.respond(NicknameCheckResponse(true))
            } else {
                call.respond(NicknameCheckResponse(false))
            }
        }
        delete("{userId}") {
            val id = call.parameters["userId"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (userList.removeIf { it.userId == id }) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}