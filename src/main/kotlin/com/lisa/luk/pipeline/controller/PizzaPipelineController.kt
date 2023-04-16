package com.lisa.luk.pipeline.controller

import com.lisa.luk.api.database.ToppingRepositorySqliteImpl
import com.lisa.luk.api.domain.ToppingCount
import com.lisa.luk.pipeline.PizzaPipelineOptions
import com.lisa.luk.pipeline.domain.Coders
import com.lisa.luk.pipeline.fetcher.DataFetcherService
import org.apache.beam.sdk.Pipeline
import org.apache.beam.sdk.io.jdbc.JdbcIO
import org.apache.beam.sdk.io.jdbc.JdbcIO.DataSourceConfiguration
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/pizza")
class PizzaPipelineController(
    private val pipelineOptions: PizzaPipelineOptions,
    private val toppingRepository: ToppingRepositorySqliteImpl
) {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/publish")
    fun publishPizzaToppings() {

        val pipeline = Pipeline.create(pipelineOptions)
        val coders = pipeline.coderRegistry
        Coders.registerCoders(coders)

        val dataSourceConfiguration = DataSourceConfiguration.create(
            "org.sqlite.JDBC", "jdbc:sqlite:sample.db"
        )

        val existingToppingCounts = toppingRepository.getToppingCounts()

        val fetchedToppingsPCollection = pipeline.apply(DataFetcherService(existingToppingCounts))

        fetchedToppingsPCollection.apply(JdbcIO.write<ToppingCount>()
            .withDataSourceConfiguration(dataSourceConfiguration)
            // we can replace existing rows because we aggregate existing data before writing
            .withStatement("INSERT or REPLACE INTO t_topping_counts(topping_name, total_count, unique_count) VALUES (?, ?, ?)")
            .withPreparedStatementSetter { element, preparedStatement ->
                preparedStatement.setString(1, element.name)
                preparedStatement.setInt(2, element.totalCount)
                preparedStatement.setInt(3, element.uniqueCount)
            })

        pipeline.run().waitUntilFinish()

    }
}