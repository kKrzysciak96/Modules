package com.example.module.presentation.utils

import com.example.module.domain.model.ModuleDomain

sealed interface PerformedActionMarker {
    object ActionDeleted : PerformedActionMarker
    object ActionAdded : PerformedActionMarker
    data class ActionUpdated(val oldModule: ModuleDomain) : PerformedActionMarker
}
