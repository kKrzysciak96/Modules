package com.example.module.domain.model

import com.example.core.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

data class ModuleDomain(
    val name: String,
    val comment: String,
    val incrementation: Int,
    val newIncrementation: Int? = null,
    val epochDay: Long,
    val isSkipped: Boolean,
    val timeStamp: Long,
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
)
