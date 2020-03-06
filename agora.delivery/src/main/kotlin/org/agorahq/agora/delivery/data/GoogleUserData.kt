package org.agorahq.agora.delivery.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleUserData(
        @JsonProperty("id")
        val id: String,
        @JsonProperty("email")
        val email: String,
        @JsonProperty("given_name")
        val firstName: String,
        @JsonProperty("family_name")
        val lastName: String)