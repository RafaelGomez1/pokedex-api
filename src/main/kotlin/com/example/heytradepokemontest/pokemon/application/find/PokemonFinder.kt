package com.example.heytradepokemontest.pokemon.application.find

import arrow.core.raise.Raise
import com.example.heytradepokemontest.pokemon.domain.FindPokemonCriteria.ById
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.application.find.FindPokemonError.PokemonNotFound
import com.example.heytradepokemontest.pokemon.domain.findByOrElse
import java.util.UUID

context(PokemonRepository, Raise<FindPokemonError>)
fun findPokemon(id: UUID): Pokemon =
    findByOrElse(ById(id)) { PokemonNotFound }.bind()

sealed class FindPokemonError {
    data object InvalidIdentifier : FindPokemonError()
    data object PokemonNotFound : FindPokemonError()
}
