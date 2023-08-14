package pokemon

import com.example.heytradepokemontest.pokemon.domain.CombatPoints
import com.example.heytradepokemontest.pokemon.domain.HealthPoints
import com.example.heytradepokemontest.pokemon.domain.Stats
import kotlin.random.Random

object StatsMother {

    fun random(
        hp: HealthPoints = Random.nextInt(),
        cp: CombatPoints = Random.nextInt()
    ) = Stats(hp = hp, cp = cp)
}
