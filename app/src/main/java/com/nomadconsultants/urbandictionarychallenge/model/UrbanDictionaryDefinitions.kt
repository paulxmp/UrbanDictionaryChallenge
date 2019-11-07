package com.nomadconsultants.urbandictionarychallenge.model

import com.squareup.moshi.Json

data class UrbanDictionaryDefinitions(
    @Json(name = "list")
    val definitions: List<UrbanDictionaryDefinition>
)





