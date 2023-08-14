package pokemon

import FakerTest
import com.example.heytradepokemontest.pokemon.domain.Name

object NameMother {

    fun random(value: String = FakerTest.faker.name.name()) = Name(value)
}
