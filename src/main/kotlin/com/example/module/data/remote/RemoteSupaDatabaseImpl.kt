package com.example.module.data.remote

import com.example.module.data.remote.model.SupabaseModuleDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class RemoteSupaDatabaseImpl(private val client: SupabaseClient) :
    RemoteDatabase<SupabaseModuleDto> {

    override suspend fun fetchModulesFromRemote(): List<SupabaseModuleDto> {

        val dataResponse = client.postgrest["test"].select()
        val data = dataResponse.decodeList<SupabaseModuleDto>()
        return data
    }

    override suspend fun pushModulesToRemote(modules: List<SupabaseModuleDto>) {
        client.postgrest["test"].delete { SupabaseModuleDto::reset isExact true }
        val result = client.postgrest["test"].insert(modules)

    }

}