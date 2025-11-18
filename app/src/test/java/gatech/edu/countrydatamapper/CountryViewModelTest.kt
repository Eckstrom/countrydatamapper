package gatech.edu.countrydatamapper

import gatech.edu.countrydatamapper.dto.CountryDto
import gatech.edu.countrydatamapper.json.CountryApi
import gatech.edu.countrydatamapper.persistence.CountryRepository
import gatech.edu.countrydatamapper.ui.CountryUiState
import gatech.edu.countrydatamapper.viewmodel.CountryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {

    @get:Rule
    val mainDispatcherRule = DispatcherForTests()

    private class FakeCountryApiNonEmpty : CountryApi {
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
            throw RuntimeException("An error occurred while fetching countries.")
        }
    }

    @Test
    fun `loadCountries emits Success when repository returns non-empty list`() = runTest {
        val viewModel = makeViewModel(FakeCountryApiNonEmpty())

        viewModel.loadCountries()

        val state = viewModel.uiState.value
        println("STATE in success test = $state")

        assertTrue("Expected Success but was $state", state is CountryUiState.Success)
        val countries = (state as CountryUiState.Success).countries
        assertEquals(1, countries.size)
        assertEquals("United States of America", countries[0].name)
    }

    @Test
    fun `loadCountries emits Error when repository returns empty list`() = runTest {
        val viewModel = makeViewModel(FakeCountryApiEmpty())

        viewModel.loadCountries()

        val state = viewModel.uiState.value
        println("STATE in empty-list test = $state")

        assertTrue("Expected Error but was $state", state is CountryUiState.Error)
        assertEquals("Country JSON is empty.", (state as CountryUiState.Error).message)
    }

    @Test
    fun `loadCountries emits Error when repository throws`() = runTest {
        val viewModel = makeViewModel(FakeCountryApiFailure())

        viewModel.loadCountries()

        val state = viewModel.uiState.value
        println("STATE in failure test = $state")

        assertTrue("Expected Error but was $state", state is CountryUiState.Error)

        val message = (state as CountryUiState.Error).message
        assertTrue("Expected non-blank error message but was '$message'", message.isNotBlank())
    }

    private fun makeViewModel(api: CountryApi): CountryViewModel {
        val repo = CountryRepository(api)
        return CountryViewModel(repository = repo)
    }
}
