package org.agorahq.agora.delivery.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.delivery.data.UserPrincipal
import java.security.Key

@Suppress("UNCHECKED_CAST")
object JWTService {

    private const val USER_CLAIM = "user"

    private val json = Json(JsonConfiguration.Stable)
    private val key: Key = Keys.hmacShaKeyFor("a269tOWeWrkK02Ub9uQHbD9MYHKKcY/Wry+t0Vjd0bg=".toByteArray(Charsets.UTF_8))

    fun createJWSFrom(user: User): String {
        return Jwts.builder()
                .setSubject(user.username)
                .claim(USER_CLAIM, json.stringify(UserPrincipal.serializer(), user.toPrincipal()))
                .signWith(key)
                .compact()
    }

    fun createUserFrom(jws: String): UserPrincipal {
        val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws)
        return json.parse(UserPrincipal.serializer(), claims.body[USER_CLAIM].toString())
    }

    private fun User.toPrincipal() = UserPrincipal.fromUser(this)
}
