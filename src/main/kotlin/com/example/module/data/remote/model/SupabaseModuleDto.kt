package com.example.module.data.remote.model

import com.example.ModuleEntity
import com.example.core.utils.UUIDSerializer
import com.example.module.domain.model.ModuleDomain
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class SupabaseModuleDto(
    val name: String,
    val comment: String,
    val incrementation: String,
    val newIncrementation: String? = null,
    val epochDay: String,
    val isSkipped: Boolean,
    val timeStamp: String,
    val reset: Boolean,
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,

    ) {
    constructor(module: ModuleDomain) : this(
        name = module.name,
        comment = module.comment,
        incrementation = module.incrementation.toString(),
        newIncrementation = module.newIncrementation.toString(),
        epochDay = module.epochDay.toString(),
        id = module.id,
        reset = true,
        timeStamp = module.timeStamp.toString(),
        isSkipped = module.isSkipped
    )

    constructor(module: ModuleEntity) : this(
        name = module.name,
        comment = module.comment,
        incrementation = module.incrementation.toString(),
        newIncrementation = module.newIncrementation.toString(),
        epochDay = module.epochDay.toString(),
        id = UUID.fromString(module.id),
        reset = true,
        timeStamp = module.timeStamp.toString(),
        isSkipped = module.isSkipped == 1L
    )

    fun toModule() = ModuleDomain(
        name = name,
        comment = comment,
        incrementation = incrementation.toInt(),
        newIncrementation = newIncrementation?.toIntOrNull(),
        epochDay = epochDay.toLong(),
        id = id,
        isSkipped = isSkipped,
        timeStamp = timeStamp.toLong()
    )

    fun toModuleEntity(): ModuleEntity {
        return ModuleEntity(
            id = this.id.toString(),
            name = this.name,
            comment = this.comment,
            incrementation = this.incrementation.toLong(),
            newIncrementation = this.newIncrementation?.toLongOrNull(),
            epochDay = this.epochDay.toLong(),
            isSkipped = if (this.isSkipped) 1 else 0,
            timeStamp = this.timeStamp.toLong(),
        )
    }

}