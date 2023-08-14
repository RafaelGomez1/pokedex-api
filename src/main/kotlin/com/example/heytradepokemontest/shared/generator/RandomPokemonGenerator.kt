package com.example.heytradepokemontest.shared.generator

import com.example.heytradepokemontest.pokemon.domain.Height
import com.example.heytradepokemontest.pokemon.domain.Name
import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.Stats
import com.example.heytradepokemontest.pokemon.domain.Type
import com.example.heytradepokemontest.pokemon.domain.Weight
import java.util.UUID
import kotlin.random.Random

typealias PokemonGenerator = (names: List<String>) -> List<Pokemon>

val generateRandomPokemon: PokemonGenerator =  { names ->
    names.map { name ->
        Pokemon.register(
            id = UUID.randomUUID(),
            name = Name(value = name),
            types = listOf(Type.values().random(), Type.values().random()),
            weight = Weight(value = Random.nextDouble(0.0, 1000.0)),
            height = Height(value = Random.nextDouble(0.0, 7.0)),
            stats = Stats(hp = Random.nextInt(0, 400), cp = Random.nextInt(0, 1200)),
            favorite = false
        )
    }
}
