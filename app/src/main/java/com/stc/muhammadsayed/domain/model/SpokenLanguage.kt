package com.stc.muhammadsayed.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//this typically should contain DTO as well and mapper toDomain and fromDomain
@Parcelize
class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String? = null,
    @SerializedName("name")
    val name: String? = null
): Parcelable