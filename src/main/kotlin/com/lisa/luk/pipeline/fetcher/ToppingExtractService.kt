package com.lisa.luk.pipeline.fetcher

import com.lisa.luk.api.domain.ToppingCount
import com.lisa.luk.pipeline.domain.EmailToppingsTuple
import com.lisa.luk.pipeline.domain.Topping
import java.io.Serializable

class ToppingExtractService : Serializable {

    fun extractAndValidateToppingCounts(
        emailToToppingsPairs: List<EmailToppingsTuple>,
        existingToppingCounts: List<ToppingCount>
    ): MutableCollection<ToppingCount> {

        // create a map of existing toppings to their counts so we can aggregate on the fly on the write side
        val existingToppingsMap = existingToppingCounts.associateBy { Topping.caseInsensitiveValueOf(it.name) }

        val toppingCountMap = mutableMapOf<Topping, ToppingCount>()
        for (pair in emailToToppingsPairs) {
            // use this to help determine the unique count per topping
            val toppingsByUser = mutableSetOf<Topping>()
            val toppings = pair.second

            for (topping in toppings) {
                // validate that we carry the topping the user asked for
                val toppingValue = Topping.caseInsensitiveValueOf(topping) ?: continue

                // if valid topping, increment total and unique topping counts
                val currToppingCount = toppingCountMap[toppingValue]
                if (currToppingCount != null) {
                    if (!toppingsByUser.contains(toppingValue)) {
                        // only increment unique count if this is a new user
                        val currUniqueCount = currToppingCount.uniqueCount
                        currToppingCount.uniqueCount = currUniqueCount + 1
                    }
                    val currTotalCount = currToppingCount.totalCount
                    currToppingCount.totalCount = currTotalCount + 1
                } else {
                    // check to see if there is an existing count for this topping
                    val existingToppingCount = existingToppingsMap[toppingValue]
                    val toppingCountToInsert = if (existingToppingCount != null) {
                        ToppingCount(
                            toppingValue.toppingName,
                            totalCount = existingToppingCount.totalCount + 1,
                            existingToppingCount.uniqueCount + 1
                        )
                        // create a new instance if this is the first occurrence of the topping
                    } else ToppingCount(name = toppingValue.toppingName, totalCount = 1, uniqueCount = 1)
                    toppingCountMap[toppingValue] = toppingCountToInsert
                }
                toppingsByUser.add(toppingValue)
            }
        }
        return toppingCountMap.values
    }
}