package com.capella.network

import android.content.Context
import android.graphics.drawable.Drawable
import kotlin.random.Random
import androidx.core.content.ContextCompat
import com.capella.R

object PicGenerator{

    val samplePics = listOf(R.drawable.rb19,

        R.drawable.rb19_2,
        R.drawable.max_helmet,
        R.drawable.helmet_sideways,
        R.drawable.max_winning,
        R.drawable.max_1finger,
        R.drawable.max_helmet_bw,
        R.drawable.max_helmet_side_shot
    )

    /**
     * Return a random drawable resource id from the provided list.
     * uses sample pics by default.
     * Usage:
     * val ids = listOf(R.drawable.pic1, R.drawable.pic2, R.drawable.pic3)
     * val resId = PicGenerator.randomRes(ids)
     */
    fun randomRes(list: List<Int> = samplePics): Int {
        require(list.isNotEmpty()) { "list must not be empty" }
        return list[Random.nextInt(list.size)]
    }

    /**
     * Return a random Drawable from the provided resource id list.
     * Returns null if the resource cannot be loaded.
     * Uses sample pics by default.
     * Usage:
     * val drawable = PicGenerator.randomDrawable(context, ids)
     */
    fun randomDrawable(context: Context, list: List<Int> = samplePics): Drawable? {
        val resId = randomRes(list)
        return ContextCompat.getDrawable(context, resId)
    }
}