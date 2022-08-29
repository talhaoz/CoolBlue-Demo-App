package com.talhaoz.coolblueapp.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Context.showToastLong(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.showToastShort(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Fragment.navigate(navDirections: NavDirections) =
    this.findNavController().navigate(navDirections)

fun ImageView.loadImage(url: String) =
    Glide.with(this.context)
        .load(url)
        .into(this)