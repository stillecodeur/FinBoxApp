package com.anirudh.finboxapp.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anirudh.finboxapp.data.models.LocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationInfoViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {
    val getAllInfo: Flow<PagingData<LocationInfo>> =
        locationRepository.allLocationInfo.cachedIn(viewModelScope)
}