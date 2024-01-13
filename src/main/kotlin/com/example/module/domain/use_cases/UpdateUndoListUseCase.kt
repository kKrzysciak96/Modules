package com.example.module.domain.use_cases

class UpdateUndoListUseCase {

//    operator fun invoke(
//        currentListPair: List<List<Pair<PerformedActionMarker, Module>>>,
//        currentIndex: Int?,
//        newSlot: List<Pair<PerformedActionMarker, Module>>
//    ): List<List<Pair<PerformedActionMarker, Module>>> {
//        val newList: MutableList<List<Pair<PerformedActionMarker, Module>>>
//        val currentSize = currentListPair.size
//        when (currentIndex) {
//            null -> {
//                newList = listOf(newSlot).toMutableList()
//            }
//
//            currentSize - 1 -> {
//                newList = currentListPair.toMutableList()
//                newList.add(newSlot)
//            }
//
//            else -> {
//                newList = currentListPair.take(currentIndex + 1).toMutableList()
//                newList.add(newSlot)
//            }
//        }
//        return newList
//    }
}