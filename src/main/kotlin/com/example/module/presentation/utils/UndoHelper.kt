package com.example.module.presentation.utils

import com.example.module.domain.model.ModuleDomain

data class UndoHelper(
    var undoList: List<List<Pair<PerformedActionMarker, ModuleDomain>>> = emptyList(),
    var undoIndex: Int? = null
) {
    fun updateUndoList(newSlot: List<Pair<PerformedActionMarker, ModuleDomain>>) {
        undoIndex.let { currentUndoIndex ->

            val newList = createNewList(
                currentListPair = undoList.map { list ->
                    list.map {
                        Pair(it.first, it.second)
                    }
                },
                newSlot = newSlot.map {
                    Pair(it.first, it.second)
                },
                currentIndex = currentUndoIndex
            )
            undoList = newList.map { list ->
                list.map {
                    Pair(it.first, it.second)
                }
            }
            undoIndex = newList.size - 1

        }
        updateUndoIndex(undoList.size - 1)
    }

    private fun updateUndoIndex(newIndex: Int?) {
        undoIndex = newIndex

    }

    private fun createNewList(
        currentListPair: List<List<Pair<PerformedActionMarker, ModuleDomain>>>,
        currentIndex: Int?,
        newSlot: List<Pair<PerformedActionMarker, ModuleDomain>>
    ): List<List<Pair<PerformedActionMarker, ModuleDomain>>> {
        val newList: MutableList<List<Pair<PerformedActionMarker, ModuleDomain>>>
        val currentSize = currentListPair.size
        when (currentIndex) {
            null -> {
                newList = listOf(newSlot).toMutableList()
            }

            currentSize - 1 -> {
                newList = currentListPair.toMutableList()
                newList.add(newSlot)
            }

            else -> {
                newList = currentListPair.take(currentIndex + 1).toMutableList()
                newList.add(newSlot)
            }
        }
        return newList
    }
}