package com.example.elqh.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Categoria(
    val nombre: String,
    val opciones: List<String>
) : Parcelable