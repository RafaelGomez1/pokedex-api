package com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable


data class PokemonDTO(
    val id: String,
    val name: String,
    val types: List<String>,
    val weight: WeightDTO,
    val height: HeightDTO,
    val stats: StatsDTO,
    val isFavorite: Boolean
)

data class WeightDTO(
    val value: Double,
    val unit: String
)

data class HeightDTO(
    val value: Double,
    val unit: String
)

data class StatsDTO(
    val hp: Int,
    val cp: Int,
)
