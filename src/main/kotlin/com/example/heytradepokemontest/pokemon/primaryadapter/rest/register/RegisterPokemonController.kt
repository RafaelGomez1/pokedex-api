package com.example.heytradepokemontest.pokemon.primaryadapter.rest.register

import arrow.core.raise.fold
import com.example.heytradepokemontest.pokemon.application.register.RegisterPokemonError
import com.example.heytradepokemontest.pokemon.application.register.RegisterPokemonError.Unknown
import com.example.heytradepokemontest.pokemon.application.register.registerPokemons
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.shared.error.Response
import com.example.heytradepokemontest.shared.error.withoutBody
import com.example.heytradepokemontest.shared.generator.generateRandomPokemon
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisterPokemonController(private val repository: PokemonRepository) {

    private val pokemonNames = listOf("Dratini", "Dragonair", "Dragonite", "Mewtwo", "Mew")

    @PostMapping("/pokemons")
    fun register(): Response<*> =
        with(repository) {
            fold(
                block = { registerPokemons(generateRandomPokemon(pokemonNames)) },
                recover = { error -> error.toServerError() },
                transform = { Response.status(HttpStatus.CREATED).withoutBody() }
            )
        }

    private fun RegisterPokemonError.toServerError(): Response<*> =
        when(this) {
            is Unknown -> throw reason
        }
}
