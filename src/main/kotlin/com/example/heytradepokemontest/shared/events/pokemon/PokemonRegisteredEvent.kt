package com.example.heytradepokemontest.shared.events.pokemon

import com.example.heytradepokemontest.shared.DomainEvent

data class PokemonRegisteredEvent(
    val id: String,
    val name: String,
    val type: String,
    val weight: Double,
    val height: Double,
    val hp: Int,
    val cp: Int
) : DomainEvent(id)
