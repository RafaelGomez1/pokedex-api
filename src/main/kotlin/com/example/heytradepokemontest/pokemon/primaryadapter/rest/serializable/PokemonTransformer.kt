package com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable

import com.example.heytradepokemontest.pokemon.application.find.FindPokemonQueryResponse
import com.example.heytradepokemontest.pokemon.domain.Pokemon

object PokemonTransformer {

    fun toDocument(queryResponse: FindPokemonQueryResponse) =
        with(queryResponse) {
            PokemonDTO(
                id = id,
                name = name,
                types = types,
                weight = WeightDTO(
                    value = weight,
                    unit = weightUnit
                ),
                height = HeightDTO(
                    value = height,
                    unit = heightUnit
                ),
                stats = StatsDTO(
                    hp = hp,
                    cp = cp,
                ),
                isFavorite = isFavorite
            )
        }

    fun toDocument(pokemon: Pokemon) =
        with(pokemon) {
            PokemonDTO(
                id = id.toString(),
                name = name.value,
                types = types.map { it.name },
                weight = WeightDTO(
                    value = weight.value,
                    unit = weight.unit
                ),
                height = HeightDTO(
                    value = height.value,
                    unit = height.unit
                ),
                stats = StatsDTO(
                    hp = stats.hp,
                    cp = stats.cp,
                ),
                isFavorite = favorite
            )
        }
}
