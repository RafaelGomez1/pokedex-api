package com.example.heytradepokemontest.pokemon.application.mark

import arrow.core.raise.Raise
import com.example.heytradepokemontest.pokemon.domain.FindPokemonCriteria
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.domain.findByOrElse
import com.example.heytradepokemontest.pokemon.domain.saveOrElse
import com.example.heytradepokemontest.pokemon.application.mark.MarkPokemonError.Unknown
import com.example.heytradepokemontest.pokemon.application.mark.MarkPokemonError.PokemonNotFound
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import java.util.UUID

context(PokemonRepository, Raise<MarkPokemonError>)
fun markPokemon(id: UUID): Pokemon =
    findByOrElse(FindPokemonCriteria.ById(id)) { PokemonNotFound }.bind()
        .mark()
        .saveOrElse { error -> Unknown(error) }.bind()

sealed class MarkPokemonError {
    data object PokemonNotFound : MarkPokemonError()
    data object InvalidIdentifier : MarkPokemonError()
    class Unknown(val reason: Throwable) : MarkPokemonError()
}
