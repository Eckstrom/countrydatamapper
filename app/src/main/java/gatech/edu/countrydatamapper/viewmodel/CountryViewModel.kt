package gatech.edu.countrydatamapper.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gatech.edu.countrydatamapper.persistence.CountryRepository
import gatech.edu.countrydatamapper.ui.CountryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountryUiState>(CountryUiState.Idle)
    val uiState: StateFlow<CountryUiState> = _uiState

    fun loadCountries() {
        _uiState.value = CountryUiState.Loading

        // Launch a coroutine to fetch countries asynchronously
        viewModelScope.launch {
            val result = repository.fetchCountries()
            _uiState.value = result.fold(
                // Handle success and error cases appropriately.
                // Among failures, either:
                // 1. List was empty
                // 2. Some other exception occurred during fetch
                onSuccess = { list ->
                    if (list.isEmpty()) {
                        CountryUiState.Error("Country JSON is empty.")
                    } else {
                        CountryUiState.Success(list)
                    }
                },
                onFailure = { e ->
                    CountryUiState.Error(e.message ?: "Unknown error. Please try again later.")
                }
            )
        }
    }
}