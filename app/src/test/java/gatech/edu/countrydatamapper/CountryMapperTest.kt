package gatech.edu.countrydatamapper

import gatech.edu.countrydatamapper.dto.CountryDto
import gatech.edu.countrydatamapper.mapping.toCountry
import org.junit.Assert.assertEquals
import org.junit.Test

class CountryMapperTest {

    @Test
    fun `toCountry maps non-null fields correctly`() {
        val dto = CountryDto(
            name = "United States of America",
            region = "NA",
            capital = "Washington, D.C.",
            code = "US",
            currency = null,
            language = null
        )

        val country = dto.toCountry()

        assertEquals("United States of America", country.name)
        assertEquals("NA", country.region)
        assertEquals("Washington, D.C.", country.capital)
        assertEquals("US", country.code)
    }

    @Test
    fun `toCountry replaces nulls with Unknown`() {
        val dto = CountryDto(
            name = null,
            region = null,
            capital = null,
            code = null,
            currency = null,
            language = null
        )

        val country = dto.toCountry()

        assertEquals("Unknown", country.name)
        assertEquals("Unknown", country.region)
        assertEquals("Unknown", country.capital)
        assertEquals("Unknown", country.code)
    }
}
