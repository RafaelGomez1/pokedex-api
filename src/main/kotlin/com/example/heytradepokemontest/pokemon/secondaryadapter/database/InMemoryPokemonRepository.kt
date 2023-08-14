package com.example.heytradepokemontest.pokemon.secondaryadapter.database

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
import org.springframework.stereotype.Component

@Component
class InMemoryPokemonRepository : PokemonRepository {
    private val pokemons = mutableListOf<Pokemon>()

    override fun save(pokemon: Pokemon): Either<Throwable, Unit> = catch {
        if (pokemons.any { pk -> pk.id == pokemon.id })
            pokemons[pokemons.indexOfFirst { it.id == pokemon.id }] = pokemon
        else pokemons.add(pokemon)
    }

    override fun findBy(criteria: FindPokemonCriteria): Either<Throwable, Pokemon> = catch {
        when(criteria) {
            is ById -> pokemons.first { pokemon -> pokemon.id == criteria.id }
        }
    }

    override fun searchBy(criteria: SearchPokemonCriteria): Either<Throwable, List<Pokemon>> = catch {
        when(criteria) {
            is None -> pokemons
            is ByFavorite -> pokemons.filter { pokemon -> pokemon.favorite }
            is ByName -> pokemons.filter { pokemon -> pokemon.name.value.contains(criteria.name.value, true)  }
            is ByType -> pokemons.filter { pokemon -> pokemon.types.contains(criteria.type) }
            is ByNameAndFavorite -> pokemons.filter { pokemon -> pokemon.name.value.contains(criteria.name.value, true) && pokemon.favorite }
            is ByTypeAndFavorite -> pokemons.filter { pokemon -> pokemon.types.contains(criteria.type) && pokemon.favorite }
            is ByTypeAndName ->  pokemons.filter { pokemon -> pokemon.name.value.contains(criteria.name.value, true) && pokemon.types.contains(criteria.type) }
            is ByTypeAndNameAndFavorite -> pokemons.filter { pokemon -> pokemon.name.value.contains(criteria.name.value, true) && pokemon.types.contains(criteria.type) && pokemon.favorite }
        }
    }
}
