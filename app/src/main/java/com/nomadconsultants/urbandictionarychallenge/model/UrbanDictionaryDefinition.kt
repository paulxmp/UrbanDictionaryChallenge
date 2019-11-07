package com.nomadconsultants.urbandictionarychallenge.model

import com.squareup.moshi.Json

data class UrbanDictionaryDefinition(
    @Json(name = "definition")
    val definition: String,
    @Json(name = "permalink")
    val permalink: String,
    @Json(name = "thumbs_up")
    val thumbsUp: Int = 0,
    @Json(name = "sound_urls")
    val soundUrls: List<String>,
    @Json(name = "author")
    val author: String,
    @Json(name = "word")
    val word: String,
    @Json(name = "defid")
    val defid: Int = 0,
    @Json(name = "current_vote")
    val currentVote: String,
    @Json(name = "written_on")
    val writtenOn: String,
    @Json(name = "example")
    val example: String,
    @Json(name = "thumbs_down")
    val thumbsDown: Int = 0
)