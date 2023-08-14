package com.example.heytradepokemontest.pokemon.fakes

import arrow.core.Either
import arrow.core.Either.Companion.catch
import com.example.heytradepokemontest.pokemon.domain.FindPokemonCriteria
import com.example.heytradepokemontest.pokemon.domain.FindPokemonCriteria.ById
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.PokemonRepository
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria.ByTypeAndNameAndFavorite
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria.None
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria.ByFavorite
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria.ByName
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria.ByType
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria.ByNameAndFavorite
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria.ByTypeAndFavorite
import com.example.heytradepokemontest.pokemon.domain.SearchPokemonCriteria.ByTypeAndName
import com.example.heytradepokemontest.shared.FakeRepository

object FakePokemonRepository : FakeRepository<Pokemon>, PokemonRepository {
    override val elements = mutableListOf<Pokemon>()

    override fun save(pokemon: Pokemon): Either<Throwable, Unit> = catch {
        if (elements.any { pk -> pk.id == pokemon.id })
            elements[elements.indexOfFirst { it.id == pokemon.id }] = pokemon
        else elements.add(pokemon)
    }
    override fun findBy(criteria: FindPokemonCriteria): Either<Throwable, Pokemon> = catch {
        when(criteria) {
            is ById -> elements.first { pokemon -> pokemon.id == criteria.id }
        }
    }

    override fun searchBy(criteria: SearchPokemonCriteria): Either<Throwable, List<Pokemon>> = catch {
        when(criteria) {
            is None -> elements
            is ByFavorite -> elements.filter { pokemon -> pokemon.favorite }
            is ByName -> elements.filter { pokemon -> pokemon.name.value.contains(criteria.name.value, true)  }
            is ByType -> elements.filter { pokemon -> pokemon.types.contains(criteria.type) }
            is ByNameAndFavorite -> elements.filter { pokemon -> pokemon.name.value.contains(criteria.name.value, true) && pokemon.favorite }
            is ByTypeAndFavorite -> elements.filter { pokemon -> pokemon.types.contains(criteria.type) && pokemon.favorite }
            is ByTypeAndName ->  elements.filter { pokemon -> pokemon.name.value.contains(criteria.name.value, true) && pokemon.types.contains(criteria.type) }
            is ByTypeAndNameAndFavorite -> elements.filter { pokemon -> pokemon.name.value.contains(criteria.name.value, true) && pokemon.types.contains(criteria.type) && pokemon.favorite }
        }
    }
}
