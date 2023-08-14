package pokemon

import com.example.heytradepokemontest.pokemon.domain.Height
import kotlin.random.Random

object HeightMother {
    fun random(value: Double = Random.nextDouble()) = Height(value = value)
}
