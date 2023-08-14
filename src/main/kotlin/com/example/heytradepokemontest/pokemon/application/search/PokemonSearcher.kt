package com.example.heytradepokemontest.pokemon.application.search

import arrow.core.raise.Raise
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria
import com.example.heytradepokemontest.pokemon.domain.searchByOrElse
import com.example.heytradepokemontest.pokemon.application.search.SearchPokemonError.Unknown

context(PokemonRepository, Raise<SearchPokemonError>)
fun searchPokemons(criteria: SearchPokemonCriteria): List<Pokemon> =
    searchByOrElse(criteria) { error -> Unknown(error) }.bind()

sealed class SearchPokemonError {
    data object InvalidPokemonType : SearchPokemonError()
    class Unknown(val reason: Throwable) : SearchPokemonError()
}
