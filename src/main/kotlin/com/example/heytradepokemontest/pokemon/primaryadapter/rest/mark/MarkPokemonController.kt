package com.example.heytradepokemontest.pokemon.primaryadapter.rest.mark

import arrow.core.raise.fold
import com.example.heytradepokemontest.pokemon.application.mark.MarkPokemonCommand
import com.example.heytradepokemontest.pokemon.application.mark.MarkPokemonError
import com.example.heytradepokemontest.pokemon.application.mark.MarkPokemonError.InvalidIdentifier
import com.example.heytradepokemontest.pokemon.application.mark.MarkPokemonError.PokemonNotFound
import com.example.heytradepokemontest.pokemon.application.mark.MarkPokemonError.Unknown
import com.example.heytradepokemontest.pokemon.application.mark.handle
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.error.PokemonErrors.INVALID_IDENTIFIERS
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.error.PokemonErrors.POKEMON_NOT_FOUND
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.PokemonTransformer
import com.example.heytradepokemontest.shared.error.Response
import com.example.heytradepokemontest.shared.error.withBody
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MarkPokemonController(private val repository: PokemonRepository) {

    private val transformer = PokemonTransformer

    @PatchMapping("/pokemons/{pokemonId}/mark")
    fun mark(@PathVariable pokemonId: String): Response<*> =
        with(repository) {
            fold(
                block = { handle(MarkPokemonCommand(pokemonId)) },
                recover = { error -> error.toServerError() },
                transform = { markedPokemon -> Response.status(OK).body(transformer.toDocument(markedPokemon)) }
            )
        }

    private fun MarkPokemonError.toServerError() =
        when (this) {
            is PokemonNotFound -> Response.status(NOT_FOUND).withBody(POKEMON_NOT_FOUND)
            is InvalidIdentifier -> Response.status(BAD_REQUEST).withBody(INVALID_IDENTIFIERS)
            is Unknown -> throw reason
        }
}
