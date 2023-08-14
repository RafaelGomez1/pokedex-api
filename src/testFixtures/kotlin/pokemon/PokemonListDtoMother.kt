package pokemon

import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.PokemonListDTO
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.PokemonListElement

object PokemonListDtoMother {

    fun fromPokemons(vararg pokemons: Pokemon) =
        PokemonListDTO(
            pokemons.map { pokemon ->
                PokemonListElement(
                    id = pokemon.id.toString(),
                    name = pokemon.name.value,
                    types = pokemon.types.map { it.name },
                    isFavorite = pokemon.favorite
                )
            }
        )
}
