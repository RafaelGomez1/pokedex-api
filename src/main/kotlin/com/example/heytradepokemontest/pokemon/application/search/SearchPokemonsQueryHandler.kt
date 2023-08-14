package com.example.heytradepokemontest.pokemon.application.search

import arrow.core.raise.Raise
import arrow.core.raise.catch
import com.example.heytradepokemontest.pokemon.application.find.FindPokemonQueryResponse
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.domain.Type
import com.example.heytradepokemontest.pokemon.application.search.SearchPokemonError.InvalidPokemonType
import com.example.heytradepokemontest.pokemon.domain.Name
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria

context(PokemonRepository, Raise<SearchPokemonError>)
fun handle(query: SearchPokemonQuery): SearchPokemonQueryResponse {
    val type = catch({ query.type?.let { Type.valueOf(it) } }) { raise(InvalidPokemonType) }
    val name = query.name?.let { Name(it) }

    val criteria = SearchPokemonCriteria.build(type, name, query.favorite)
    val pokemons = searchPokemons(criteria)

    return SearchPokemonQueryResponse(pokemons.map { it.toQueryResponse() })
}

private fun Pokemon.toQueryResponse(): PokemonResponse =
    PokemonResponse(
        id = id.toString(),
        name = name.value,
        types = types.map { it.name },
        isFavorite = favorite
    )

data class SearchPokemonQuery(val type: String?, val name: String?, val favorite: Boolean)
data class SearchPokemonQueryResponse(val pokemons: List<PokemonResponse>)

data class PokemonResponse(
    val id: String,
    val name: String,
    val types: List<String>,
    val isFavorite: Boolean
)
