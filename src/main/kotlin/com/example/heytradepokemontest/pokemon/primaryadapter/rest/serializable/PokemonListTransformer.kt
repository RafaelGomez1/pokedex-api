package com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable

import com.example.heytradepokemontest.pokemon.application.search.SearchPokemonQueryResponse

object PokemonListTransformer {

    fun fromQueryResponse(searchQueryResponse: SearchPokemonQueryResponse) =
        PokemonListDTO(
            searchQueryResponse.pokemons.map { pokemon ->
                PokemonListElement(
                    id = pokemon.id,
                    name = pokemon.name,
                    types = pokemon.types,
                    isFavorite = pokemon.isFavorite
                )
            }
        )
}
