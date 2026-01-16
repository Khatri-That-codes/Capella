package com.capella.models

import com.squareup.moshi.Json

data class QuoteDTO(
    @Json(name = "q")
    val quoteText: String,
    @Json(name = "a")
    val quoteAuthor: String,
    @Json(name = "h")
    val h: String? = null
)
{

    fun toDomain(): Quote {
        return Quote(
            text = quoteText,
            author = quoteAuthor
        )
    }
}

