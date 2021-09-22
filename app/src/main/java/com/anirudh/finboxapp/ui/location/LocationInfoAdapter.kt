package com.anirudh.finboxapp.ui.location

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anirudh.finboxapp.data.models.LocationInfo
import com.anirudh.finboxapp.databinding.LocationInfoRowBinding
import com.anirudh.finboxapp.utils.AppUtils

class LocationInfoAdapter(val locationInfos: List<LocationInfo>) :
    RecyclerView.Adapter<LocationInfoAdapter.LocationInfoHolder>() {

    inner class LocationInfoHolder(private val locationInfoRowBinding: LocationInfoRowBinding) :
        RecyclerView.ViewHolder(locationInfoRowBinding.root) {
        fun bind(locationInfo: LocationInfo) {
            with(locationInfoRowBinding) {
                tvLatitudeValue.text = locationInfo.latitude?.toString()
                tvLongitudeValue.text = locationInfo.longitude?.toString()
                if (locationInfo.time != null) {
                    tvDateTime.text = AppUtils.convertLongToTime(locationInfo.time!!)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationInfoHolder {
        val binding =
            LocationInfoRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationInfoHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationInfoHolder, position: Int) {
        val info = locationInfos[position]
        if (info.latitude != null && info.longitude != null && info.time != null) {
            holder.bind(info)
        }
    }


    override fun getItemCount(): Int {
        return locationInfos.size
    }
}