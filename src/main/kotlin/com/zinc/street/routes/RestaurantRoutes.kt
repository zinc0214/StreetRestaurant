package com.zinc.street.routes

import com.zinc.street.dto.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.utils.io.*

fun Application.registerRestaurantRoutes() {
    routing {
        restaurantRouting()
    }
}

fun Route.restaurantRouting() {
    route("/restaurant") {
        get {
            if (restaurantList.isNotEmpty()) {
                call.respond(restaurantList)
            } else {
                call.respondText("No restaurant found", status = HttpStatusCode.NotFound)
            }
        }
        get("{postId}") {
            val id = call.parameters["postId"] ?: return@get call.respond(
                Response(
                    HttpStatusCode.BadRequest.value,
                    "Missing or malformed id"
                )
            )
            val restaurant =
                restaurantList.find { it.postId == id } ?: return@get call.respond(
                    Response(
                        HttpStatusCode.OK.value,
                        "No customer with id $id"
                    )
                )

            call.respond(restaurant)
        }
        post("/write") {
            val restaurantInfo = call.receive<RequestRestaurantInfo>()
            val restaurant = Restaurant(
                postId = "${restaurantList.size}",
                writerId = listOf(restaurantInfo.writerId),
                name = restaurantInfo.name,
                address = restaurantInfo.address,
                memo = restaurantInfo.memo,
                point = Point(restaurantInfo.pointX, restaurantInfo.pointY),
                openTime = restaurantInfo.openTime,
                closeTime = restaurantInfo.closeTime,
                type = restaurantInfo.type
            )
            restaurantList.add(restaurant)
            call.respond(
                Response(
                    HttpStatusCode.OK.value
                )
            )
        }
        delete("{postId}") {
            val id = call.parameters["postId"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val deleteRestaurant = restaurantList.firstOrNull { it.postId == id }
            if (deleteRestaurant == null) {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            } else {
                restaurantList.remove(deleteRestaurant)
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            }
        }
    }
}