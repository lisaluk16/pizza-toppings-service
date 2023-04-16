package com.lisa.luk.api.database

import com.lisa.luk.api.domain.ToppingCount
import jakarta.annotation.PostConstruct
import org.ktorm.database.Database
import org.ktorm.dsl.forEach
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import org.sqlite.SQLiteException

@Repository
class ToppingRepositorySqliteImpl(
    private val database: Database
) : ToppingRepository {

    private val logger = LoggerFactory.getLogger(ToppingRepository::class.java)

    @PostConstruct
    fun preloadData() {
        database.useConnection { conn ->
            conn.createStatement().use { statement ->
                javaClass.classLoader
                    ?.getResourceAsStream(INIT_SCRIPT)
                    ?.bufferedReader()
                    ?.use { reader ->
                        for (sql in reader.readText().split(';')) {
                            if (sql.any { it.isLetterOrDigit() }) {
                                statement.executeUpdate(sql)
                            }
                        }
                    }
            }
        }
    }

    override fun getToppingCounts(): List<ToppingCount> {
        // get list of all topping counts
        val toppingCounts = mutableListOf<ToppingCount>()

        try {
            database.from(ToppingCounts)
                .select()
                .forEach { row ->
                    toppingCounts.add(
                        ToppingCount(
                            name = row[ToppingCounts.name]!!,
                            totalCount = row[ToppingCounts.totalCount] ?: 0,
                            uniqueCount = row[ToppingCounts.uniqueCount] ?: 0
                        )
                    )
                }
            return toppingCounts
        } catch (ex: SQLiteException) {
            logger.error("Error encountered when querying for toppings: ", ex)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found in database or table does not exist.")
        }
    }

    companion object {
        private const val INIT_SCRIPT = "init-sqlite-tables.sql"
        // use this script instead to pre-populate mock data into the table
        // private const val INIT_SCRIPT = "init-sqlite-tables.sql"
    }
}