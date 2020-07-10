package org.agorahq.agora.delivery.data

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class SessionStateTest {

    val json = Json(JsonConfiguration.Stable)

    @Test
    fun shouldSerializeAuthenticatedUserStateProperly() {

        val state = AuthenticatedUserState(
                principal = UserPrincipal(
                        firstName = "jon",
                        lastName = "doe",
                        roles = setOf(),
                        email = "a@b.com",
                        username = "jondoe",
                        id = UUID.randomUUID().toString()
                )
        )

        val serialized = json.stringify(SessionState.serializer(), state)
        val result = json.parse(SessionState.serializer(), serialized)

        assertEquals(state, result)
    }

    @Test
    fun shouldSerializeRegisteringUserStateProperly() {

        val state = RegisteringState(
                oauthUser = GoogleUser(
                        email = "a@b.com",
                        firstName = "Jon",
                        lastName = "Doe"
                )
        )

        val serialized = json.stringify(SessionState.serializer(), state)
        val result = json.parse(SessionState.serializer(), serialized)

        assertEquals(state, result)
    }
}
