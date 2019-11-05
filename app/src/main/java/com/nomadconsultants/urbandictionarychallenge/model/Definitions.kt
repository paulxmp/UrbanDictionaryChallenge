package com.nomadconsultants.urbandictionarychallenge.model

import com.squareup.moshi.Json

data class Definitions(
    @Json(name = "list")
    val definitions: List<Definition>
)





