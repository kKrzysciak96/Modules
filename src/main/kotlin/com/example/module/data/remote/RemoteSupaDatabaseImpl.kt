package com.example.module.data.remote

import com.example.module.data.remote.model.SupabaseSpecificModule
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class RemoteSupaDatabaseImpl(private val client: SupabaseClient) :
    RemoteDatabase<SupabaseSpecificModule> {

    override suspend fun fetchModulesFromRemote(): List<SupabaseSpecificModule> {

        val dataResponse = client.postgrest["test"].select()
        val data = dataResponse.decodeList<SupabaseSpecificModule>()
        return data
    }

//    override suspend fun pushModulesToRemote(modules: List<SupabaseSpecificModule>) {
//        client.postgrest["test"].delete { SupabaseSpecificModule::reset isExact true }
//        val result = client.postgrest["test"].insert(modules)
//
//    }

}