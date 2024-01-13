package com.example.module.domain.use_cases

import com.example.module.domain.model.ModuleDomain

class FilterAllModuleNames() {

    operator fun invoke(modules: List<ModuleDomain>): List<String> {
        return modules.map { it.name }.distinct()

    }
}