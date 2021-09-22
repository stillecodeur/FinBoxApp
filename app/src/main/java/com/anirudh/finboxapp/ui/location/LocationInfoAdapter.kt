package com.anirudh.finboxapp.ui.location

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anirudh.finboxapp.data.models.LocationInfo
import com.anirudh.finboxapp.databinding.LocationInfoRowBinding
import com.anirudh.finboxapp.utils.AppUtils

class LocationInfoAdapter() :PagingDataAdapter<LocationInfo,LocationInfoAdapter.
LocationInfoHolder>(DIFF_CALLBACK) {

    companion object{
        val DIFF_CALLBACK=object :DiffUtil.ItemCallback<LocationInfo>(){
            override fun areItemsTheSame(oldItem: LocationInfo, newItem: LocationInfo): Boolean =oldItem.id==newItem.id
            override fun areContentsTheSame(oldItem: LocationInfo, newItem: LocationInfo): Boolean {
                return oldItem==newItem
            }

        }
    }

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
        val info=getItem(position)
        if (info?.latitude != null && info.longitude != null && info.time != null) {
            holder.bind(info)
        }
    }



}