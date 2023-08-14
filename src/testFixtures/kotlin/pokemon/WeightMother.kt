package pokemon

import com.example.heytradepokemontest.pokemon.domain.Weight
import kotlin.random.Random

object WeightMother {
    fun random(value: Double = Random.nextDouble()) = Weight(value = value)
}
