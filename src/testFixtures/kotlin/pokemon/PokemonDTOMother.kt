package pokemon

import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.HeightDTO
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.PokemonDTO
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.StatsDTO
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.WeightDTO

object PokemonDTOMother {

    fun fromPokemon(pokemon: Pokemon) =
        with(pokemon) {
            PokemonDTO(
                id = id.toString(),
                name = name.value,
                types = types.map { it.name },
                weight = WeightDTO(
                    value = weight.value,
                    unit = weight.unit
                ),
                height = HeightDTO(
                    value = height.value,
                    unit = height.unit
                ),
                stats = StatsDTO(
                    hp = stats.hp,
                    cp = stats.cp,
                ),
                isFavorite = favorite
            )
        }

}
