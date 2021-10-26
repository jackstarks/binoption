package com.newoption.binatraderapps.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var image: Int, var title: String, var body: String,var image2: Int) :
    Parcelable