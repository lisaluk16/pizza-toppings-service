package com.lisa.luk.api.configuration

import org.ktorm.database.Database
import org.ktorm.logging.Slf4jLoggerAdapter
import org.ktorm.support.sqlite.SQLiteDialect
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PizzaServiceConfiguration {

    private val logger = LoggerFactory.getLogger(PizzaServiceConfiguration::class.java)

    @Bean
    fun connect(): Database {

        return Database.connect(
            url = "jdbc:sqlite:sample.db",
            logger = Slf4jLoggerAdapter(logger),
            dialect = SQLiteDialect()
        )
    }

}