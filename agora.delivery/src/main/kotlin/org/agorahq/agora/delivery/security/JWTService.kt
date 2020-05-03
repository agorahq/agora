package org.agorahq.agora.delivery.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.agorahq.agora.delivery.data.AuthenticatedUserState
import org.agorahq.agora.delivery.data.SessionState
import java.security.Key

@Suppress("UNCHECKED_CAST")
object JWTService {

    private const val SESSION_STATE_CLAIM = "session_state"

    private val json = Json(JsonConfiguration.Stable)
    private val key: Key = Keys.hmacShaKeyFor("a269tOWeWrkK02Ub9uQHbD9MYHKKcY/Wry+t0Vjd0bg=".toByteArray(Charsets.UTF_8))

    fun createJWSFrom(state: SessionState): String {
        val username = when (state) {
            is AuthenticatedUserState -> state.principal.username
            else -> ""
        }
        return Jwts.builder()
                .setSubject(username)
                .claim(SESSION_STATE_CLAIM, json.stringify(SessionState.serializer(), state))
                .signWith(key)
                .compact()
    }

    fun extractStateFrom(jws: String): SessionState {
        val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws)
        return json.parse(SessionState.serializer(), claims.body[SESSION_STATE_CLAIM].toString())
    }

}
