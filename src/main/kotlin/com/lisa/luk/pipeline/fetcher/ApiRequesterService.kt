package com.lisa.luk.pipeline.fetcher

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lisa.luk.pipeline.domain.EmailToppingsTuple
import org.slf4j.LoggerFactory
import java.io.Serializable
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.UUID

class ApiRequesterService: Serializable {

    private val objectMapper = jacksonObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    }
    private val logger = LoggerFactory.getLogger(ApiRequesterService::class.java)

    @Transient
    private val httpClient = HttpClient.newHttpClient()


    fun callExternalApi(externalApiUrl: String): List<EmailToppingsTuple> {
        val httpRequest = HttpRequest.newBuilder(URI.create(externalApiUrl))
            .headers(REQUEST_ID_HEADER, UUID.randomUUID().toString(), CLIENT_ID_HEADER, "lisa-luk", CONTENT_TYPE_HEADER, CONTENT_TYPE_VALUE)
            .GET()
            .build()
        val result = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
        val body = result.body()
        val statusCode = result.statusCode()

        if (result.statusCode() != 200) {
            logger.error("Non-200 value returned from external API: $statusCode with body: $body")
        } else {
            logger.info("Response from external api was: $body with status: $statusCode")
        }

        return objectMapper.readValue(body, object : TypeReference<List<EmailToppingsTuple>>() {})

    }

    companion object {
        private const val REQUEST_ID_HEADER = "x-request-id"
        private const val CLIENT_ID_HEADER = "x-client-id"
        private const val CONTENT_TYPE_HEADER = "Content-Type"
        private const val CONTENT_TYPE_VALUE = "application/json"
    }
}