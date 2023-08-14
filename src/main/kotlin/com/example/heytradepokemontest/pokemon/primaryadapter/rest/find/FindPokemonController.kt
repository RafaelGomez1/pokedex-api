package com.example.heytradepokemontest.pokemon.primaryadapter.rest.find

import arrow.core.raise.fold
import com.example.heytradepokemontest.pokemon.application.find.FindPokemonError
import com.example.heytradepokemontest.pokemon.application.find.FindPokemonError.InvalidIdentifier
import com.example.heytradepokemontest.pokemon.application.find.FindPokemonError.PokemonNotFound
import com.example.heytradepokemontest.pokemon.application.find.FindPokemonQuery
import com.example.heytradepokemontest.pokemon.application.find.handle
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.error.PokemonErrors.INVALID_IDENTIFIERS
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.error.PokemonErrors.POKEMON_NOT_FOUND
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.PokemonTransformer
import com.example.heytradepokemontest.shared.error.Response
import com.example.heytradepokemontest.shared.error.withBody
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FindPokemonController(private val repository: PokemonRepository) {

    private val transformer = PokemonTransformer

    @GetMapping("/pokemons/{pokemonId}")
    fun find(@PathVariable pokemonId: String): Response<*> =
        with(repository) {
            fold(
                block = { handle(FindPokemonQuery(pokemonId)) },
                recover = { error -> error.toServerError() },
                transform = { response -> Response.status(OK).body(transformer.toDocument(response)) }
            )
        }

    private fun FindPokemonError.toServerError() =
        when (this) {
            is InvalidIdentifier -> Response.status(BAD_REQUEST).withBody(INVALID_IDENTIFIERS)
            is PokemonNotFound -> Response.status(NOT_FOUND).withBody(POKEMON_NOT_FOUND)
        }
}
