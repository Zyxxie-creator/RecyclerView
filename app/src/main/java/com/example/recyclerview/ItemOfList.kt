package com.example.recyclerview

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
@Parcelize
data class ItemOfList(
    val imageSrc: Int,
    val ImageTitle: String,
    val imageDest:String

) :Parcelable