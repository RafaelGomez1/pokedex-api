package com.example.heytradepokemontest.pokemon.primaryadapter.rest.search

import arrow.core.raise.fold
import com.example.heytradepokemontest.pokemon.application.search.SearchPokemonError
import com.example.heytradepokemontest.pokemon.application.search.SearchPokemonError.InvalidPokemonType
import com.example.heytradepokemontest.pokemon.application.search.SearchPokemonError.Unknown
import com.example.heytradepokemontest.pokemon.application.search.SearchPokemonQuery
import com.example.heytradepokemontest.pokemon.application.search.handle
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.error.PokemonErrors.INVALID_POKEMON_TYPE
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.PokemonListTransformer
import com.example.heytradepokemontest.shared.error.Response
import com.example.heytradepokemontest.shared.error.withBody
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchPokemonsController(private val repository: PokemonRepository) {

    private val transformer = PokemonListTransformer

    @GetMapping("/pokemons")
    fun search(@RequestParam params: Map<String, String>): Response<*> =
        with(repository) {
            fold(
                block = { handle(createQuery(params)) },
                recover = { error -> error.toServerError() },
                transform = { response -> Response.status(HttpStatus.OK).body(transformer.fromQueryResponse(response)) }
            )
        }

    private fun SearchPokemonError.toServerError() =
        when (this) {
            is InvalidPokemonType -> Response.status(BAD_REQUEST).withBody(INVALID_POKEMON_TYPE)
            is Unknown -> throw this.reason
        }

    private fun createQuery(params: Map<String, String>): SearchPokemonQuery {
        val type = params.getOrElse("type") { null }
        val name = params.getOrElse("name") { null }
        val favorite = params.getOrElse("favorites") { "false" }

        return SearchPokemonQuery(type, name, favorite.toBoolean())
    }
}
