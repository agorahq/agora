package org.agorahq.agora.delivery.extensions

import org.agorahq.agora.delivery.data.GoogleUser
import kotlin.test.Test

class StringExtensionsTest {

    val googleJson = """
{
  "id": "242342342343",
  "email": "arold.adam@gmail.com",
  "verified_email": true,
  "name": "Adam Arold",
  "given_name": "Adam",
  "family_name": "Arold",
  "picture": "https://lh3.googleusercontent.com/a-/AOh14GhLiILWPj1j9nyf38MSz1QJQ1IM3f1qofMpLKPH",
  "locale": "en"
}
""".trimIndent()


    @Test
    fun testDeserialization() {

        googleJson.deserializeTo(GoogleUser.serializer())
    }
}