package com.lisa.luk.pipeline.domain

import org.apache.beam.repackaged.core.org.apache.commons.lang3.EnumUtils
import org.apache.beam.repackaged.core.org.apache.commons.lang3.StringUtils

/**
 * List of valid toppings that we carry
 */
enum class Topping(val toppingName: String) {
    CHEESE("cheese"),
    PEPPERONI("pepperoni"),
    SAUSAGE("sausage"),
    MUSHROOM("mushroom"),
    OLIVE("olive"),
    BANANA_PEPPER("banana pepper"),
    GREEN_PEPPER("green pepper"),
    CHILI_FLAKES("chili flakes");

    companion object {
        private var toppingNameToToppingMap: Map<String, Topping>

        init {
            val map = mutableMapOf<String, Topping>()
            for (topping in EnumUtils.getEnumList(Topping::class.java)) {
                map[topping.toppingName] = topping
            }
            toppingNameToToppingMap = map
        }

        fun caseInsensitiveValueOf(value: String): Topping? {
            return toppingNameToToppingMap[StringUtils.lowerCase(value)]
        }
    }
}