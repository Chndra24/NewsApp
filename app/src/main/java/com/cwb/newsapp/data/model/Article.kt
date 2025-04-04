package com.cwb.newsapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val author: String?,
    val title: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val url: String?
) : Parcelable


