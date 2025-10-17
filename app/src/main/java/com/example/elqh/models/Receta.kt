package com.example.elqh.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Receta(
    val id: String,
    val nombre: String,
    val ingredientes: List<String>,
    @DrawableRes val imagen: Int,
    val instrucciones: String
) : Parcelable
