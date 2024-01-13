package com.example.core.extensions

import com.example.ModuleEntity
import com.example.module.domain.model.ModuleDomain
import java.util.*

fun ModuleEntity.toModuleDomain() = ModuleDomain(
    id = UUID.fromString(this.id),
    name = this.name,
    comment = this.comment,
    incrementation = this.incrementation.toInt(),
    newIncrementation = this.newIncrementation?.toInt(),
    epochDay = this.epochDay,
    isSkipped = this.isSkipped == 1L,
    timeStamp = this.timeStamp
)