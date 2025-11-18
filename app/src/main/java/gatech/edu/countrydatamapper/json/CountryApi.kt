package gatech.edu.countrydatamapper.json

import gatech.edu.countrydatamapper.Constants.JSON_URL
import gatech.edu.countrydatamapper.dto.CountryDto
import retrofit2.http.GET

interface CountryApi {

    @GET(JSON_URL)
    suspend fun getCountries(): List<CountryDto>
}