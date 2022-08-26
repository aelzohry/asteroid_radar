package com.udacity.aelzohry.asteroid_radar.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.udacity.aelzohry.asteroid_radar.R
import com.udacity.aelzohry.asteroid_radar.domain.Asteroid
import com.udacity.aelzohry.asteroid_radar.domain.PictureOfDay
import com.udacity.aelzohry.asteroid_radar.ui.main.MainAdapter

@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
        )
        .into(this)
}

@BindingAdapter("android:visibility")
fun View.bindVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("asteroidsList")
fun RecyclerView.bindAsteroidsList(list: List<Asteroid>?) {
    val adapter = adapter as MainAdapter
    adapter.submitList(list)
}

@BindingAdapter("statusIcon")
fun ImageView.bindAsteroidStatusImage(isHazardous: Boolean) {
    contentDescription = if (isHazardous) {
        setImageResource(R.drawable.ic_status_potentially_hazardous)
        context.getString(R.string.potentially_hazardous_asteroid)
    } else {
        setImageResource(R.drawable.ic_status_normal)
        context.getString(R.string.not_hazardous_asteroid)
    }
}

@BindingAdapter("asteroidStatusImage")
fun ImageView.bindDetailsStatusImage(isHazardous: Boolean) {
    contentDescription = if (isHazardous) {
        setImageResource(R.drawable.asteroid_hazardous)
        context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        setImageResource(R.drawable.asteroid_safe)
        context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("pictureOfDayContentDescription")
fun ImageView.bindPictureOfDayContentDescription(picture: PictureOfDay?) {
    contentDescription = if (picture != null) {
        context.getString(R.string.nasa_picture_of_day_content_description_format, picture.title)
    } else {
        context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
    }
}

@BindingAdapter("astronomicalUnitText")
fun TextView.bindAstronomicalUnit(number: Double) {
    val context = context
    text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun TextView.bindKmUnit(number: Double) {
    val context = context
    text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun TextView.bindDisplayVelocity(number: Double) {
    val context = context
    text = String.format(context.getString(R.string.km_s_unit_format), number)
}