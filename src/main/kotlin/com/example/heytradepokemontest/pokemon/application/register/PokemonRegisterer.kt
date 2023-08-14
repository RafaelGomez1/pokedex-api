package com.example.heytradepokemontest.pokemon.application.register

import arrow.core.raise.Raise
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.domain.saveOrElse
import com.example.heytradepokemontest.pokemon.application.register.RegisterPokemonError.Unknown

context(PokemonRepository, Raise<RegisterPokemonError>)
fun registerPokemon(pokemon: Pokemon) =
    pokemon.saveOrElse { error -> Unknown(error) }

context(PokemonRepository, Raise<RegisterPokemonError>)
fun registerPokemons(pokemons: List<Pokemon>) =
    pokemons.map { pokemon -> registerPokemon(pokemon) }

sealed class RegisterPokemonError {
    class Unknown(val reason: Throwable) : RegisterPokemonError()
}
