package com.example.module.domain.repository

import com.example.core.utils.ApiResult
import com.example.module.domain.model.ModuleDomain
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ModuleRepository {


    suspend fun editModule(module: ModuleDomain)

    suspend fun addModule(module: ModuleDomain)

    suspend fun addModules(modules: List<ModuleDomain>)

    suspend fun deleteModule(module: ModuleDomain)

    suspend fun deleteModules(modules: List<ModuleDomain>)

    fun getModules(): Flow<List<ModuleDomain>>

    suspend fun getModule(id: UUID): ModuleDomain

    suspend fun dropDatabase()

    suspend fun pushModulesToRemote(): Flow<ApiResult>

    suspend fun fetchModulesFromRemote(): Flow<ApiResult>

    suspend fun saveLastCard(page: Int)

    suspend fun loadLastCard(): Int

}