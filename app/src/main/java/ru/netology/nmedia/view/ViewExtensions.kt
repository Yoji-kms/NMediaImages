package ru.netology.nmedia.view

import android.widget.ImageView
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop

fun ImageView.load(url: String, vararg transforms: BitmapTransformation = emptyArray()) =
    Glide.with(this)
        .load(url)
        .timeout(10_000)
        .transform(*transforms)
        .into(this)

fun ImageView.loadCircleCrop(url: String, vararg transforms: BitmapTransformation = emptyArray()) =
    load(url, CircleCrop(), *transforms)

fun ImageView.loadCircleCropPlaceholder(placeholderId: Int) =
    Glide.with(this)
        .load(placeholderId)
        .circleCrop()
        .into(this)

fun RecyclerView.smoothScrollToTop() {
    layoutManager?.startSmoothScroll(object :
        LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }.apply { targetPosition = 0 })
}
