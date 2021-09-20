package com.anirudh.finboxapp.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anirudh.finboxapp.data.models.LocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationInfoViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {
    val getAllInfo: LiveData<List<LocationInfo>> get() = locationRepository.allLocationInfo

    fun insertLocationInfo(locationInfo: LocationInfo) {
        viewModelScope.launch {
            locationRepository.insert(locationInfo)
        }
    }
}