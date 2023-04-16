package com.lisa.luk.pipeline

import org.apache.beam.sdk.options.Description
import org.apache.beam.sdk.options.PipelineOptions
import org.apache.beam.sdk.options.Validation

interface PizzaPipelineOptions : PipelineOptions {

    @Description("API endpoint to call to fetch user email and topping data")
    @Validation.Required
    fun getExternalApiEndpoint(): String

    fun setExternalApiEndpoint(value: String)
}