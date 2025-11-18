package gatech.edu.countrydatamapper.ui
import gatech.edu.countrydatamapper.mapping.Country

sealed class CountryUiState {
    object Idle : CountryUiState()
    object Loading : CountryUiState()
    data class Success(val countries: List<Country>) : CountryUiState()
    data class Error(val message: String) : CountryUiState()
}
