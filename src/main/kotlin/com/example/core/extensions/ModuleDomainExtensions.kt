package com.example.core.extensions

import com.example.ModuleEntity
import com.example.module.domain.model.ModuleDomain

fun ModuleDomain.toModuleEntity() = ModuleEntity(
    id = this.id.toString(),
    name = this.name,
    comment = this.comment,
    incrementation = this.incrementation.toLong(),
    newIncrementation = this.newIncrementation?.toLong(),
    epochDay = this.epochDay,
    isSkipped = if (this.isSkipped) 1 else 0,
    timeStamp = this.timeStamp
)