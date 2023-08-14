package com.example.heytradepokemontest.pokemon.unmark

import com.example.heytradepokemontest.pokemon.fakes.FakePokemonRepository
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.error.PokemonErrors.INVALID_IDENTIFIERS
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.error.PokemonErrors.POKEMON_NOT_FOUND
import com.example.heytradepokemontest.pokemon.primaryadapter.rest.unmark.UnmarkPokemonController
import com.example.heytradepokemontest.shared.error.ServerError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import pokemon.PokemonDTOMother
import pokemon.PokemonMother

class UnmarkPokemonTest {

    private val repository = FakePokemonRepository
    private val controller = UnmarkPokemonController(repository)

    @BeforeEach
    fun setUp() {
        repository.resetFake()
    }

    @Test
    fun `should unmark a marked pokemon`() {
        // Given
        repository.save(pokemon)

        // When
        val result = controller.unmark(pokemon.id.toString())

        // Then
        assertEquals(OK, result.statusCode)
        assertEquals(expected, result.body)
    }

    @Test
    fun `should fail if pokemon is not found`() {
        // When
        val result = controller.unmark(pokemon.id.toString())

        // Then
        assertEquals(NOT_FOUND, result.statusCode)
        assertEquals(ServerError.of(POKEMON_NOT_FOUND), result.body)
    }

    @Test
    fun `should fail if the id is not a uuid`() {
        // Given
        repository.save(pokemon)

        // When
        val result = controller.unmark("12345")

        // Then
        assertEquals(BAD_REQUEST, result.statusCode)
        assertEquals(ServerError.of(INVALID_IDENTIFIERS), result.body)
    }

    private val pokemon = PokemonMother.random(favorite = true)
    private val expected = PokemonDTOMother.fromPokemon(pokemon.copy(favorite = false))
}
