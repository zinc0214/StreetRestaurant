package com.zinc.street.dto

import kotlinx.serialization.Serializable

@Serializable
data class RequestRestaurantInfo(
    val writerId: String,
    val address: String,
    val pointX: Double,
    val pointY: Double,
    val name: String,
    val memo: String? = null,
    val type: String,
    val openTime: String? = null,
    val closeTime: String? = null
)


@Serializable
data class Restaurant(
    var postId: String,
    val writerId: List<String>,
    val address: String,
    val point: Point,
    val name: String,
    val memo: String? = null,
    val type: String,
    val openTime: String? = null,
    val closeTime: String? = null,
    val correctScore: Float = 0f
)

@Serializable
data class Point(val x: Double, val y: Double)

enum class RestaurantType {
    BONSIK, TACOYAKI, BOOGBBNAG
}

val restaurantList = mutableListOf<Restaurant>()