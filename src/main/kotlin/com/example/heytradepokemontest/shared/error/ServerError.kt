package com.example.heytradepokemontest.shared.error

import org.springframework.http.ResponseEntity

data class ServerError(val code: String, val description: String? = null) {
    companion object {
        fun of(error: Pair<String, String>) = ServerError(error.first, error.second)
    }
}

typealias Response<T> = ResponseEntity<T>

fun ResponseEntity.BodyBuilder.withBody(error: Pair<String, String>): Response<ServerError> = body(ServerError.of(error))

fun ResponseEntity.BodyBuilder.withoutBody(): Response<ServerError> = body(null)
