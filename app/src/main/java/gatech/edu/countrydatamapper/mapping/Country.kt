package gatech.edu.countrydatamapper.mapping

import gatech.edu.countrydatamapper.dto.CountryDto

data class Country(
    val name: String,
    val region: String,
    val capital: String,
    val code: String
)
fun CountryDto.toCountry(): Country {
    return Country(
        name = name.orUnknown(),
        region = region.orUnknown(),
        capital = capital.orUnknown(),
        code = code.orUnknown()
    )
}

private fun String?.orUnknown(): String {
    return this ?: "Unknown"
}