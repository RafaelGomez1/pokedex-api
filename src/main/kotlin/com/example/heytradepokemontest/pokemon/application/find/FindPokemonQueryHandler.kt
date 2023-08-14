package com.example.heytradepokemontest.pokemon.application.find

import arrow.core.raise.Raise
import arrow.core.raise.catch
import com.example.heytradepokemontest.pokemon.application.find.FindPokemonError.InvalidIdentifier
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import java.util.UUID


context(PokemonRepository, Raise<FindPokemonError>)
fun handle(query: FindPokemonQuery): FindPokemonQueryResponse  {
    val id = catch({ UUID.fromString(query.id) }) { raise(InvalidIdentifier) }

    return findPokemon(id)
        .toQueryResponse()
}

private fun Pokemon.toQueryResponse() =
    FindPokemonQueryResponse(
        id = id.toString(),
        name = name.value,
        types = types.map { it.name },
        weight = weight.value,
        weightUnit = weight.unit,
        height = height.value,
        heightUnit = height.unit,
        hp = stats.hp,
        cp = stats.cp,
        isFavorite = favorite
    )


data class FindPokemonQuery(val id: String)
data class FindPokemonQueryResponse(
    val id: String,
    val name: String,
    val types: List<String>,
    val weight: Double,
    val weightUnit: String,
    val height: Double,
    val heightUnit: String,
    val hp: Int,
    val cp: Int,
    val isFavorite: Boolean
)
