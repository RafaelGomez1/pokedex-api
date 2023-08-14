package com.example.heytradepokemontest.pokemon.domain

import arrow.core.Either
import java.util.UUID

interface PokemonRepository {
    fun save(pokemon: Pokemon): Either<Throwable, Unit>
    fun findBy(criteria: FindPokemonCriteria): Either<Throwable, Pokemon>
    fun searchBy(criteria: SearchPokemonCriteria): Either<Throwable, List<Pokemon>>
}

context(PokemonRepository)
fun <E> findByOrElse(criteria: FindPokemonCriteria, onError: () -> E): Either<E, Pokemon> =
    findBy(criteria)
        .mapLeft { onError() }

context(PokemonRepository)
fun <E> searchByOrElse(criteria: SearchPokemonCriteria, onError: (Throwable) -> E): Either<E, List<Pokemon>> =
    searchBy(criteria)
        .mapLeft { error -> onError(error) }

context(PokemonRepository)
fun <E> Pokemon.saveOrElse(onError: (Throwable) -> E): Either<E, Pokemon> =
    save(this)
        .map { this }
        .mapLeft { error -> onError(error) }

sealed class FindPokemonCriteria {
    class ById(val id: UUID) : FindPokemonCriteria()
}

sealed class SearchPokemonCriteria {
    class ByType(val type: Type) : SearchPokemonCriteria()
    class ByName(val name: Name) : SearchPokemonCriteria()
    class ByTypeAndName(val type: Type, val name: Name) : SearchPokemonCriteria()
    class ByTypeAndFavorite(val type: Type) : SearchPokemonCriteria()
    class ByNameAndFavorite(val name: Name) : SearchPokemonCriteria()
    class ByTypeAndNameAndFavorite(val type: Type, val name: Name) : SearchPokemonCriteria()
    object None : SearchPokemonCriteria()
    object ByFavorite : SearchPokemonCriteria()

    companion object {
        fun build(type: Type?, name: Name?, favorite: Boolean): SearchPokemonCriteria =
            when {
                type != null && name != null && !favorite-> ByTypeAndName(type, name)
                type != null && name == null && favorite -> ByTypeAndFavorite(type)
                type == null && name != null && favorite -> ByNameAndFavorite(name)
                type != null && name != null && favorite -> ByTypeAndNameAndFavorite(type, name)
                type != null -> ByType(type)
                name != null -> ByName(name)
                favorite -> ByFavorite
                else -> None
            }
    }
}
