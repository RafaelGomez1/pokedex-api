package com.example.heytradepokemontest.pokemon.application.unmark

import arrow.core.raise.Raise
import arrow.core.raise.catch
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.application.unmark.UnmarkPokemonError.InvalidIdentifier
import java.util.UUID

context(PokemonRepository, Raise<UnmarkPokemonError>)
fun handle(command: UnmarkPokemonCommand): Pokemon {
    val id = catch({ UUID.fromString(command.pokemonId) }, { raise(InvalidIdentifier) })

    return unmarkPokemon(id)
}


data class UnmarkPokemonCommand(val pokemonId: String)
