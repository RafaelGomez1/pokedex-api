package com.example.heytradepokemontest.pokemon.domain

import com.example.heytradepokemontest.shared.Aggregate
import java.util.UUID

data class Pokemon(
    val id: UUID,
    val name: Name,
    val types: List<Type>,
    val weight: Weight,
    val height: Height,
    val stats: Stats,
    val favorite: Boolean,
    val url: URL
): Aggregate() {

    companion object {
        fun register(
            id: UUID,
            name: Name,
            types: List<Type> = emptyList(),
            weight: Weight,
            height: Height,
            stats: Stats,
            favorite: Boolean = false,
            url: URL = "https://pokemon-test-cdn/$id.png"
        ) = Pokemon(id, name, types, weight, height, stats, favorite, url)
    }

    fun mark() = copy(favorite = true)
    fun unmark() = copy(favorite = false)
}

@JvmInline
value class Name(val value: String)

data class Weight(val value: Double, val unit: String = "kg")
data class Height(val value: Double, val unit: String = "m")

data class Stats(
    val hp: HealthPoints,
    val cp: CombatPoints
)

typealias HealthPoints = Int
typealias CombatPoints = Int
typealias URL = String

enum class Type {
    NORMAL,
    FIGHTING,
    FLYING,
    POISON,
    GROUND,
    ROCK,
    BUG,
    GHOST,
    STEEL,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    PSYCHIC,
    ICE,
    DRAGON,
    DARK,
    FAIRY
}
