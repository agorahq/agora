package org.agorahq.agora.core.api.shared.date

import com.soywiz.klock.DateTime
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("TestFunctionName")
class DatesTest {

    @Test
    fun Given_a_valid_date_When_simple_formatting_it_Then_it_should_produce_valid_output() {

        val date = DateTime.createAdjusted(2020, 7, 6)

        val result = date.format(Dates.simpleDateFormat)

        assertEquals(
                expected = "2020-07-06",
                actual = result
        )
    }
}
