package com.zinc.street.routes

import com.zinc.street.dto.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerUserRoutes() {
    routing {
        userRouting()
    }
}

fun Route.userRouting() {
    route("/user") {
        get {
            if (userList.isNotEmpty()) {
                call.respond(userList)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.NotFound)
            }
        }
        get("{userId}") {
            val id = call.parameters["userId"] ?: return@get call.respond(
                Response(
                    HttpStatusCode.BadRequest.value,
                    "Missing or malformed id"
                )
            )
            val user =
                userList.find { it.userId == id } ?: return@get call.respond(
                    Response(
                        HttpStatusCode.OK.value,
                        "No customer with id $id"
                    )
                )

            call.respond(user)
        }
        post {
            val customer = call.receive<User>()
            customer.userId = "${userList.size}_${customer.nickname.toLowerCase()}"
            userList.add(customer)
            nickNameList.add(customer.nickname)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Accepted)
        }
        delete("{userId}") {
            val id = call.parameters["userId"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val deleteUser = userList.firstOrNull { it.userId == id }
            if (deleteUser == null) {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            } else {
                userList.remove(deleteUser)
                nickNameList.removeIf { it == deleteUser.nickname }
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            }
        }
    }
}