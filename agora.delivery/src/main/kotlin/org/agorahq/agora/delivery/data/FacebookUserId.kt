package org.agorahq.agora.delivery.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class FacebookUserId(
        @JsonProperty("id")
        val id: String)
