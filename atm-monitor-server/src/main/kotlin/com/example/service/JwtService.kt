package com.example.service
import io.ktor.server.application.*
import io.ktor.server.auth.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.routing.request.UserRequest
import io.ktor.server.auth.jwt.*
import kotlinx.datetime.Clock

import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class JwtService(private val application: Application) {

    val  getProperty ={ key:String ->
        application.environment.config.config("ktor.jwt").property(key).getString()
    }

    private val secret = getProperty("secret")
    private val issuer = getProperty("issuer")
    private val audience = getProperty("audience")

    val jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()


    fun generateToken(user: UserRequest): String? {

       return JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("role",user.role.name)
            .withClaim("username", user.name)
            .withExpiresAt( Date( System.currentTimeMillis().plus(user.validity.toDuration(DurationUnit.DAYS).inWholeMilliseconds ) )     )
            .sign(Algorithm.HMAC256(secret))
    }


    fun UserValidator ( jwtCredential: JWTCredential): JWTPrincipal? {

        val username = extractUser(jwtCredential)
        val xuser = username?.let { UserService::getByUserName }
        return xuser?.let {
            if(matchAudience(jwtCredential) &&  !notExpired(jwtCredential) ) {
                JWTPrincipal(jwtCredential.payload)
            } else null

        }
    }

    private fun matchAudience(jwtCredential: JWTCredential) =
        jwtCredential.payload.audience.contains(audience)

    private fun notExpired(jwtCredential: JWTCredential) =
        jwtCredential.payload.expiresAt.before( Date(System.currentTimeMillis()) )

    private fun extractUser(jwtCredential: JWTCredential)=
        jwtCredential.payload.getClaim("username").asString()

}