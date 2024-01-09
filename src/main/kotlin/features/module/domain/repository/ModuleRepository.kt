package features.module.domain.repository

import core.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface ModuleRepository {

    suspend fun getModules(): Flow<ApiResult>

}