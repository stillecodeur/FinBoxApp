package com.anirudh.finboxapp.ui.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anirudh.finboxapp.data.models.LocationInfo
import com.anirudh.finboxapp.databinding.LocationInfoRowBinding

class LocationInfoAdapter(val locationInfos: List<LocationInfo>) :
    RecyclerView.Adapter<LocationInfoAdapter.LocationInfoHolder>() {

    inner class LocationInfoHolder(private val locationInfoRowBinding: LocationInfoRowBinding) :
        RecyclerView.ViewHolder(locationInfoRowBinding.root) {
        fun bind(locationInfo: LocationInfo) {
            with(locationInfoRowBinding) {
                tvDateTime.text = locationInfo.time.toString()
                tvLatitude.text = locationInfo.latitude.toString()
                tvLongitude.text = locationInfo.longitude.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationInfoHolder {
        val binding =
            LocationInfoRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationInfoHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationInfoHolder, position: Int) =holder.bind(locationInfos[position])

    override fun getItemCount(): Int {
        return locationInfos.size
    }
}