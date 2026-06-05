package com.shetty.pagination.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shetty.pagination.R
import com.shetty.pagination.models.Businesses
import com.shetty.pagination.utils.Constants

class PageAdapter :
    PagingDataAdapter<Businesses, PageAdapter.YelpViewHolder>(COMPARATOR) {

    class YelpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTv: TextView = itemView.findViewById(R.id.restaurant_name)
        val addressTv: TextView = itemView.findViewById(R.id.restaurant_address)
        val isOpenTv: TextView = itemView.findViewById(R.id.restaurant_open)
        val logoIv: ImageView = itemView.findViewById(R.id.restaurant_logo)

        val context = itemView.context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): YelpViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.yelp_items_layout,
                parent,
                false
            )

        return YelpViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: YelpViewHolder,
        position: Int
    ) {

        val business = getItem(position) ?: return

        holder.nameTv.text = business.name

        val address = business.location
            ?.displayAddress
            ?.joinToString(", ")
            .orEmpty()

        if (address.isNotEmpty()) {
            holder.addressTv.visibility = View.VISIBLE
            holder.addressTv.text = address
        } else {
            holder.addressTv.visibility = View.GONE
        }

        holder.isOpenTv.text =
            "${Constants.restaurantStatusMessage} ${
                if (business.isClosed == true) Constants.closed else Constants.open
            }"
        Glide.with(holder.context)
            .load(business.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground) // Replace with your placeholder
            .error(R.drawable.ic_launcher_foreground)       // Replace with your error image
            .centerCrop()
            .into(holder.logoIv)
    }

    companion object {

        private val COMPARATOR =
            object : DiffUtil.ItemCallback<Businesses>() {

                override fun areItemsTheSame(
                    oldItem: Businesses,
                    newItem: Businesses
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Businesses,
                    newItem: Businesses
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}