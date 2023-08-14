package com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable

data class PokemonListDTO(
    val pokemons: List<PokemonListElement>
)

data class PokemonListElement(
    val id: String,
    val name: String,
    val types: List<String>,
    val isFavorite: Boolean
)
