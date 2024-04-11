package com.dgioto.fairytalesinukrainian.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.dgioto.fairytalesinukrainian.models.FairyTale

class MainScreenViewModel: ViewModel() {

    private val _favoriteFairyTales = mutableStateListOf<FairyTale>()
    val favoriteFairyTales: List<FairyTale>
        get() = _favoriteFairyTales.toList()

    fun addToFavorites(fairyTale: FairyTale) {
        _favoriteFairyTales.add(fairyTale)
    }

    fun removeFromFavorites(fairyTale: FairyTale) {
        _favoriteFairyTales.remove(fairyTale)
    }
}