package com.lisa.luk.api.service

import com.lisa.luk.api.database.ToppingRepositorySqliteImpl
import com.lisa.luk.api.domain.ToppingInformation
import com.lisa.luk.api.extensions.getLeastPopularToppings
import com.lisa.luk.api.extensions.getMostPopularToppings
import com.lisa.luk.api.extensions.getToppingNames
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils

@Service
class PizzaServiceImpl(
    private val toppingRepository: ToppingRepositorySqliteImpl
) : PizzaService {

    private val logger = LoggerFactory.getLogger(PizzaServiceImpl::class.java)

    override fun retrieveToppingInformation(): ToppingInformation? {
        val toppingCounts = toppingRepository.getToppingCounts()

        if (CollectionUtils.isEmpty(toppingCounts)) return null
        if (toppingCounts.size <= 1)
            return ToppingInformation(
                toppings = toppingCounts,
                mostPopularToppings = toppingCounts.getToppingNames(),
                leastPopularToppings = listOf()
            )

        // sort the toppings list by based on total count, most popular to least popular
        val sortedList = toppingCounts.sortedByDescending { it.totalCount }

        val mostPopular = sortedList.getMostPopularToppings()
        val leastPopular = sortedList.getLeastPopularToppings()

        logger.debug("most popular toppings are: $mostPopular and least popular are: $leastPopular")

        return ToppingInformation(
            toppings = toppingCounts,
            mostPopularToppings = mostPopular.getToppingNames(),
            leastPopularToppings = leastPopular.getToppingNames()
        )

    }
}