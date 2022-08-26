package com.udacity.aelzohry.asteroid_radar.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.aelzohry.asteroid_radar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.Exception


class MainViewModel(private val repo: AsteroidRepository) : ViewModel() {

    val pictureOfDay = repo.pictureOfDay

    val asteroids = repo.asteroids

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    var showFilter = ShowAsteroidsFilter.ALL
        set(value) {
            field = value
            showAsteroids(value)
        }

    init {
        fetchPictureOfDay()
        fetchAsteroids()
        showAsteroids(showFilter)
    }

    private fun fetchPictureOfDay() {
        viewModelScope.launch {
            try {
                repo.fetchPictureOfDay()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun fetchAsteroids() {
        _loadingStatus.value = LoadingStatus.LOADING
        viewModelScope.launch {
            try {
                repo.fetchAsteroids()
                _loadingStatus.value = LoadingStatus.DONE
                showAsteroids(showFilter)
            } catch (e: Exception) {
                Timber.e(e)
                _loadingStatus.value = LoadingStatus.ERROR
            }
        }
    }

    private fun showAsteroids(filter: ShowAsteroidsFilter) {
        viewModelScope.launch {
            try {
                when (filter) {
                    ShowAsteroidsFilter.ALL -> repo.loadAllAsteroids()
                    ShowAsteroidsFilter.TODAY -> repo.loadTodayAsteroids()
                    ShowAsteroidsFilter.WEEK -> repo.loadWeekAsteroids()
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

}

enum class ShowAsteroidsFilter {
    ALL,
    TODAY,
    WEEK;
}

enum class LoadingStatus {
    LOADING,
    DONE,
    ERROR;
}