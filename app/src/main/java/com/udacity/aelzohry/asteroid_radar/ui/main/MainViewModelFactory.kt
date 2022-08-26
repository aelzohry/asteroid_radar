package com.udacity.aelzohry.asteroid_radar.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.aelzohry.asteroid_radar.repository.AsteroidRepository

class MainViewModelFactory(private val repo: AsteroidRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(repo) as T
        } else {
            throw IllegalArgumentException("Main ViewModel Not Found")
        }
    }

}