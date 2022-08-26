package com.udacity.aelzohry.asteroid_radar.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.aelzohry.asteroid_radar.R
import com.udacity.aelzohry.asteroid_radar.databinding.AsteroidItemLayoutBinding
import com.udacity.aelzohry.asteroid_radar.domain.Asteroid

class MainAdapter(private val clickListener: ClickListener) :
    ListAdapter<Asteroid, MainAdapter.AsteroidViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bindTo(asteroid, clickListener)
    }

    class AsteroidViewHolder(private val binding: AsteroidItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(asteroid: Asteroid, clickListener: ClickListener) {
            binding.asteroid = asteroid
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                clickListener.onClick(asteroid)
            }
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val binding: AsteroidItemLayoutBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.asteroid_item_layout,
                    parent,
                    false
                )

                return AsteroidViewHolder(binding)
            }
        }

    }

    object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ClickListener(val handler: (Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = handler(asteroid)
    }

}