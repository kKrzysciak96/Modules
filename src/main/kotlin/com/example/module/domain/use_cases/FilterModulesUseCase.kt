package com.example.module.domain.use_cases

import com.example.module.domain.model.ModuleDomain
import java.time.LocalDate

class FilterModulesUseCase {

    operator fun invoke(modules: List<ModuleDomain>, date: LocalDate): List<ModuleDomain> {
        val startRange = date.toEpochDay()
        val endRange = date.plusDays(13L).toEpochDay()
        return modules.filter { module -> module.epochDay in startRange until endRange }
    }
}