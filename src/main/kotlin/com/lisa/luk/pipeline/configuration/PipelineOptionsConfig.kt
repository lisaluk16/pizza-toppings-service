package com.lisa.luk.pipeline.configuration

import com.lisa.luk.pipeline.PizzaPipelineOptions
import org.apache.beam.runners.direct.DirectRunner
import org.apache.beam.sdk.options.PipelineOptionsFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PipelineOptionsConfig(@Value("\${externalApiEndpoint}") private val externalApiEndpoint: String) {

    @Bean
    fun pipelineOptions(): PizzaPipelineOptions {
        val options = PipelineOptionsFactory.create().`as`(PizzaPipelineOptions::class.java)
        options.runner = DirectRunner::class.java
        options.setExternalApiEndpoint(externalApiEndpoint)
        return options
    }
}