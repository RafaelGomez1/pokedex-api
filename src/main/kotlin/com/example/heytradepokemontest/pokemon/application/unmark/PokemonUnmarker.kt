package com.example.heytradepokemontest.pokemon.application.unmark

import arrow.core.raise.Raise
import com.example.heytradepokemontest.pokemon.domain.FindPokemonCriteria
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.domain.findByOrElse
import com.example.heytradepokemontest.pokemon.domain.saveOrElse
import com.example.heytradepokemontest.pokemon.application.unmark.UnmarkPokemonError.PokemonNotFound
import com.example.heytradepokemontest.pokemon.application.unmark.UnmarkPokemonError.Unknown
import java.util.UUID

context(PokemonRepository, Raise<UnmarkPokemonError>)
fun unmarkPokemon(id: UUID): Pokemon =
    findByOrElse(FindPokemonCriteria.ById(id)) { PokemonNotFound }.bind()
        .unmark()
        .saveOrElse { error -> Unknown(error) }.bind()

sealed class UnmarkPokemonError {
    data object PokemonNotFound : UnmarkPokemonError()
    data object InvalidIdentifier : UnmarkPokemonError()
    class Unknown(val reason: Throwable) : UnmarkPokemonError()
}
