package gatech.edu.countrydatamapper.persistence

import gatech.edu.countrydatamapper.json.CountryApi
import gatech.edu.countrydatamapper.mapping.Country
import gatech.edu.countrydatamapper.mapping.toCountry
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val api: CountryApi
) {
    suspend fun fetchCountries(): Result<List<Country>> =
        runCatching {
            val dtos = api.getCountries()
            dtos.map { dto -> dto.toCountry() }
        }
}
