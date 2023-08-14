package com.example.heytradepokemontest.pokemon.find

import com.example.heytradepokemontest.pokemon.fakes.FakePokemonRepository
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.error.PokemonErrors.INVALID_IDENTIFIERS
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.error.PokemonErrors.POKEMON_NOT_FOUND
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.find.FindPokemonController
import com.example.heytradepokemontest.shared.error.ServerError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import pokemon.PokemonDTOMother
import pokemon.PokemonMother

class FindPokemonTest {

    private val repository = FakePokemonRepository
    private val controller = FindPokemonController(repository)

    @BeforeEach
    fun setUp() {
        repository.resetFake()
    }

    @Test
    fun `should find existing pokemon`() {
        // Given
        repository.save(pokemon)

        // When
        val result = controller.find(pokemon.id.toString())

        // Then
        assertEquals(OK, result.statusCode)
        assertEquals(expected, result.body)
    }

    @Test
    fun `should return not found if pokemon is not found`() {
        // When
        val result = controller.find(pokemon.id.toString())

        // Then
        assertEquals(NOT_FOUND, result.statusCode)
        assertEquals(ServerError.of(POKEMON_NOT_FOUND), result.body)
    }

    @Test
    fun `should return invalid identifier if pokemon id is not a valid uuid`() {
        // Given
        repository.save(pokemon)

        // When
        val result = controller.find("122345")

        // Then
        assertEquals(BAD_REQUEST, result.statusCode)
        assertEquals(ServerError.of(INVALID_IDENTIFIERS), result.body)
    }

    private val pokemon = PokemonMother.random()
    private val expected = PokemonDTOMother.fromPokemon(pokemon)
}
