package pokemon

import com.example.heytradepokemontest.pokemon.domain.Height
import com.example.heytradepokemontest.pokemon.domain.Name
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.Stats
import com.example.heytradepokemontest.pokemon.domain.Type
import com.example.heytradepokemontest.pokemon.domain.Weight
import java.util.UUID
import kotlin.random.Random

object PokemonMother {

    fun random(
        id: UUID = UUID.randomUUID(),
        name: Name = NameMother.random(),
        types: List<Type> = listOf(Type.values().random(), Type.values().random()),
        weight: Weight = WeightMother.random(),
        height: Height = HeightMother.random(),
        stats: Stats = StatsMother.random(),
        favorite: Boolean = Random.nextBoolean()
    ) = Pokemon.register(
        id = id,
        name = name,
        types = types,
        weight = weight,
        height = height,
        stats = stats,
        favorite = favorite
    )
}
