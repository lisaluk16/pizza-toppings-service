package com.lisa.luk.api.controller

import com.lisa.luk.api.domain.ToppingInformation
import com.lisa.luk.api.service.PizzaServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class PizzaController(private val pizzaService: PizzaServiceImpl) {

    @GetMapping("/toppings")
    fun retrieveToppingInformation(): ResponseEntity<ToppingInformation> {
        val toppingInfo = pizzaService.retrieveToppingInformation()
        return if (toppingInfo != null) ResponseEntity.ok(toppingInfo) else ResponseEntity.noContent().build()
    }

}
