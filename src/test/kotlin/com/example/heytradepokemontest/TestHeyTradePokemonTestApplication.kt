package com.example.heytradepokemontest

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.with

@TestConfiguration
class TestHeyTradePokemonTestApplication

fun main(args: Array<String>) {
    fromApplication<HeyTradePokemonTestApplication>().with(TestHeyTradePokemonTestApplication::class).run(*args)
}
