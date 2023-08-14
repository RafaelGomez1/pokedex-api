package com.example.heytradepokemontest.pokemon.primaryadapter.subscriber

import arrow.core.raise.fold
import com.example.heytradepokemontest.pokemon.application.register.registerPokemon
import com.example.heytradepokemontest.pokemon.domain.Height
import com.example.heytradepokemontest.pokemon.domain.Name
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.domain.Stats
import com.example.heytradepokemontest.pokemon.domain.Type
import com.example.heytradepokemontest.pokemon.domain.Weight
import com.example.heytradepokemontest.shared.generator.PokemonGenerator
import com.example.heytradepokemontest.shared.generator.generateRandomPokemon
import java.util.UUID
import kotlin.random.Random
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class InitializeDatabaseSubscriber(private val repository: PokemonRepository) : CommandLineRunner {

    private val logger = KotlinLogging.logger {}

    override fun run(vararg args: String?) {
        val pokemons = listOf("Bulbasaur", "Yvisaur", "Venusaur", "Charmander", "Charmeleon", "Charizard", "Squirtle", "Wartortle", "Blastoise")

        generateRandomPokemon(pokemons)
            .map { pokemon -> add(pokemon) }
    }

    private fun add(pokemon: Pokemon) =
        with(repository) {
            fold(
                block = { registerPokemon(pokemon) },
                recover = { logger.info { "Failed Registering pokemon: ${pokemon.name.value}" } },
                transform = { logger.info { "Registered pokemon: ${pokemon.name.value}" } }
            )
        }
}
