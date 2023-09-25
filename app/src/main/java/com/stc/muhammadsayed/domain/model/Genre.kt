package com.stc.muhammadsayed.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//this typically should contain DTO as well and mapper toDomain and fromDomain
@Parcelize
data class Genre(
    var id: Int,
    var name: String? = null
) : Parcelable