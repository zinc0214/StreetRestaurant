package com.zinc.street.dto

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class User(var userId: String? = null, val nickname: String, val email: String)

val userList = mutableListOf<User>()
val nickNameList = mutableListOf<String>()

@Serializable
data class Response(var code: Int, var desc: String = "")

@Serializable
data class NicknameCheck(var nickname: String)

@Serializable
data class NicknameCheckResponse(var usable : Boolean)
