package com.lisa.luk.pipeline.fetcher

import com.lisa.luk.api.domain.ToppingCount
import com.lisa.luk.pipeline.PizzaPipelineOptions
import com.lisa.luk.pipeline.domain.Coders
import org.apache.beam.sdk.transforms.Create
import org.apache.beam.sdk.transforms.PTransform
import org.apache.beam.sdk.values.PBegin
import org.apache.beam.sdk.values.PCollection

class DataFetcherService(private val existingToppingCounts: List<ToppingCount>) : PTransform<PBegin, PCollection<ToppingCount>>() {
    private val extractAndValidationService = ToppingExtractService()
    private val apiRequesterService = ApiRequesterService()

    override fun expand(input: PBegin): PCollection<ToppingCount> {
        val pipeline = input.pipeline
        val options = pipeline.options.`as`(PizzaPipelineOptions::class.java)

        // API returns tuple of user emails to corresponding topping choices
        val apiResponse = apiRequesterService.callExternalApi(options.getExternalApiEndpoint())

        val toppingCounts = extractAndValidationService.extractAndValidateToppingCounts(apiResponse, existingToppingCounts)

        return input.apply(
            Create.of(toppingCounts)
                .withCoder(Coders.toppingCountCoder())
        )
    }
}