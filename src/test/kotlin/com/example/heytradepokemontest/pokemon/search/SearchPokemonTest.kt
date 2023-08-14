package com.example.heytradepokemontest.pokemon.search

import com.example.heytradepokemontest.pokemon.domain.Pokemon
import com.example.heytradepokemontest.pokemon.domain.Type
import com.example.heytradepokemontest.pokemon.fakes.FakePokemonRepository
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.search.SearchPokemonsController
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.PokemonDTO
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.serializable.PokemonListDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.OK
import pokemon.PokemonDTOMother
import pokemon.PokemonListDtoMother
import pokemon.PokemonMother

class SearchPokemonTest {

    private val repository = FakePokemonRepository
    private val controller = SearchPokemonsController(repository)

    @BeforeEach
    fun setUp() {
        repository.resetFake()
    }

    @Test
    fun `should find all pokemons`() {
        // Given
        pokemonExist(pokemon1, pokemon2, pokemon3, pokemon4)

        // When
        val result = controller.search(emptyMap())

        // Then
        val expected = PokemonListDtoMother.fromPokemons(pokemon1, pokemon2, pokemon3, pokemon4)

        assertEquals(OK, result.statusCode)
        assertEquals(expected, result.body)
    }

    @Test
    fun `should find all pokemons by type`() {
        // Given
        pokemonExist(pokemon1, pokemon2, pokemon3, pokemon4)

        // When
        val requestParams = mapOf("type" to "DRAGON")
        val result = controller.search(requestParams)

        // Then
        val expected = PokemonListDtoMother.fromPokemons(pokemon1, pokemon2)

        assertEquals(OK, result.statusCode)
        assertEquals(expected, result.body)
    }

    @Test
    fun `should find pokemons by name`() {
        // Given
        pokemonExist(pokemon1, pokemon2, pokemon3, pokemon4)

        // When
        val requestParams = mapOf("name" to pokemon1.name.value.substring(2))
        val result = controller.search(requestParams)

        // Then
        val expected = PokemonListDtoMother.fromPokemons(pokemon1)

        assertEquals(OK, result.statusCode)
        assertEquals(expected, result.body)
    }

    @Test
    fun `should find pokemons by favorite`() {
        // Given
        pokemonExist(pokemon1, pokemon2, pokemon3, pokemon4)

        // When
        val requestParams = mapOf("favorites" to "true")
        val result = controller.search(requestParams)

        // Then
        val expected = PokemonListDtoMother.fromPokemons(pokemon2, pokemon4)

        assertEquals(OK, result.statusCode)
        assertEquals(expected, result.body)
    }

    @Test
    fun `should find pokemons by type and favorite`() {
        // Given
        pokemonExist(pokemon1, pokemon2, pokemon3, pokemon4)

        // When
        val requestParams = mapOf("type" to "STEEL", "favorites" to "true")
        val result = controller.search(requestParams)

        // Then
        val expected = PokemonListDtoMother.fromPokemons(pokemon2)

        assertEquals(OK, result.statusCode)
        assertEquals(expected, result.body)
    }

    @Test
    fun `should find pokemons by name and favorite`() {
        // Given
        pokemonExist(pokemon1, pokemon2, pokemon3, pokemon4)

        // When
        val requestParams = mapOf("name" to pokemon2.name.value.substring(2), "favorites" to "true")
        val result = controller.search(requestParams)

        // Then
        val expected = PokemonListDtoMother.fromPokemons(pokemon2)

        assertEquals(OK, result.statusCode)
        assertEquals(expected, result.body)
    }

    @Test
    fun `should find pokemons by name and type`() {
        // Given
        pokemonExist(pokemon1, pokemon2, pokemon3, pokemon4)

        // When
        val requestParams = mapOf("name" to pokemon3.name.value.substring(2), "type" to "FIRE")
        val result = controller.search(requestParams)

        // Then
        val expected = PokemonListDtoMother.fromPokemons(pokemon3)

        assertEquals(OK, result.statusCode)
        assertEquals(expected, result.body)
    }

    @Test
    fun `should return empty if no pokemon exist`() {
        // When
        val result = controller.search(emptyMap())

        // Then
        assertEquals(OK, result.statusCode)
        assertEquals(PokemonListDTO(emptyList()), result.body)
    }

    private fun pokemonExist(vararg pokemon: Pokemon) {
        pokemon.forEach { repository.save(it) }
    }

    private val pokemon1 = PokemonMother.random(types = listOf(Type.DRAGON), favorite = false)
    private val pokemon2 = PokemonMother.random(types = listOf(Type.DRAGON, Type.STEEL), favorite = true)
    private val pokemon3 = PokemonMother.random(types = listOf(Type.FIRE), favorite = false)
    private val pokemon4 = PokemonMother.random(types = listOf(Type.FAIRY), favorite = true)
}
