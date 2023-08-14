package com.example.heytradepokemontest.pokemon.application.mark

import arrow.core.raise.Raise
import arrow.core.raise.catch
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.application.mark.MarkPokemonError.InvalidIdentifier
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import java.util.UUID

context(PokemonRepository, Raise<MarkPokemonError>)
fun handle(command: MarkPokemonCommand): Pokemon {
    val id = catch({ UUID.fromString(command.pokemonId) }, { raise(InvalidIdentifier) })

    return markPokemon(id)
}


data class MarkPokemonCommand(val pokemonId: String)
