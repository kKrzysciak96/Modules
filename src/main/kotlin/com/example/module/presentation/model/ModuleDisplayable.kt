package com.example.module.presentation.model


import com.example.module.domain.model.ModuleDomain
import java.util.*

data class ModuleDisplayable(
    val name: String,
    val comment: String,
    val incrementation: String,
    val newIncrementation: Int? = null,
    val epochDay: Long,
    val id: UUID,
    val isModuleDropdownMenuVisible: Boolean = false,
    val isAddNewIncrementDropdownMenuVisible: Boolean = false,
    val isAddNewIncrementFromDateDropdownMenuVisible: Boolean = false,
    val isEditIncrementDropdownMenuVisible: Boolean = false,
    val isSkipped: Boolean = false,
    val timeStamp: Long
) {
    constructor(module: ModuleDomain) : this(
        name = module.name,
        comment = module.comment,
        incrementation = module.incrementation.toString(),
        newIncrementation = module.newIncrementation,
        epochDay = module.epochDay,
        id = module.id,
        isSkipped = module.isSkipped,
        timeStamp = module.timeStamp
    )

    fun toModuleDomain() = ModuleDomain(
        name = name,
        comment = comment,
        incrementation = incrementation.toInt(),
        newIncrementation = newIncrementation,
        epochDay = epochDay,
        id = id,
        isSkipped = isSkipped,
        timeStamp = timeStamp
    )

    fun prepareDescriptionText(): String {

        return if (newIncrementation != null) {
            newIncrementation.toString() + "_" + name + " "
        } else {
            "$name "
        }
    }
}