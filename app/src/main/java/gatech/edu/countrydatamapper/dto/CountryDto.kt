package gatech.edu.countrydatamapper.dto

//Looking through the JSON, it's clear that fields may not always be present..
//I need to make all DTO fields nullable so it will prevent issues during deserialization
//in cases where data is missing.

data class CountryDto(
    val name: String?,
    val region: String?,
    val capital: String?,
    val code: String?,
    val currency: CurrencyDto?,
    val language: LanguageDto?
)

//TODO do I need to map currency and language to the LazyColumn's Card data too?
data class CurrencyDto(
    val code: String?,
    val name: String?,
    val symbol: String?
)

data class LanguageDto(
    val code: String?,
    val name: String?
)