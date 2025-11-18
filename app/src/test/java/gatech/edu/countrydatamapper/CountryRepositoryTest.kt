package gatech.edu.countrydatamapper

import gatech.edu.countrydatamapper.dto.CountryDto
import gatech.edu.countrydatamapper.json.CountryApi
import gatech.edu.countrydatamapper.mapping.Country
import gatech.edu.countrydatamapper.persistence.CountryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException

class CountryRepositoryTest {

    private class FakeCountryApiSuccess : CountryApi {
        override suspend fun getCountries(): List<CountryDto> =
            listOf(
                CountryDto(
                    name = "United States of America",
                    region = "NA",
                    capital = "Washington, D.C.",
                    code = "US",
                    currency = null,
                    language = null
                )
            )
    }

    private class FakeCountryApiEmpty : CountryApi {
        override suspend fun getCountries(): List<CountryDto> = emptyList()
    }

    private class FakeCountryApiFailure : CountryApi {
        override suspend fun getCountries(): List<CountryDto> {
            throw IOException("Network error")
        }
    }

    @Test
    fun `fetchCountries returns success with mapped countries`() = runBlocking {
        val repo = CountryRepository(FakeCountryApiSuccess())

        val result: Result<List<Country>> = repo.fetchCountries()

        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertNotNull(list)
        assertEquals(1, list!!.size)
        assertEquals("United States of America", list[0].name)
        assertEquals("US", list[0].code)
    }

    @Test
    fun `fetchCountries returns success with empty list`() = runBlocking {
        val repo = CountryRepository(FakeCountryApiEmpty())

        val result = repo.fetchCountries()

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!.isEmpty())
    }

    @Test
    fun `fetchCountries returns failure on exception`() = runBlocking {
        val repo = CountryRepository(FakeCountryApiFailure())

        val result = repo.fetchCountries()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IOException)
    }
}
