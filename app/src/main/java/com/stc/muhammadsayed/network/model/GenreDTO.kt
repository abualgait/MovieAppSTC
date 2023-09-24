package com.stc.muhammadsayed.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreDTO(
    var id: Int,
    var name: String? = null
) : Parcelable