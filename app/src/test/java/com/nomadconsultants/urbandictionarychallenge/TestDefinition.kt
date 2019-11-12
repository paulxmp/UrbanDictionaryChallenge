package com.nomadconsultants.urbandictionarychallenge

import com.nomadconsultants.urbandictionarychallenge.model.UrbanDictionaryDefinition
import org.junit.Assert
import org.junit.Test

class TestDefinition {
    val dummyDefinitionList = listOf(UrbanDictionaryDefinition(
        definition = "No definition found.",
        permalink = "No author found.",
        thumbsUp = 0,
        soundUrls = listOf(""),
        author = "No author found.",
        word = "No word found.",
        defid = 0,
        currentVote = "No current vote found.",
        writtenOn = "No written on found.",
        example = "No example found.",
        thumbsDown = 0
    ))

    @Test
    fun checkDefinitionToString() {
        val sb = StringBuilder()
        sb.append("[UrbanDictionaryDefinition(definition=").append("No definition found.,")
        sb.append(" permalink=").append("No author found.,")
        sb.append(" thumbsUp=").append(0.toString()).append(",")
        sb.append(" soundUrls=").append("[],")
        sb.append(" author=").append("No author found.,")
        sb.append(" word=").append("No word found.,")
        sb.append(" defid=").append(0.toString()).append(",")
        sb.append(" currentVote=").append("No current vote found.,")
        sb.append(" writtenOn=").append("No written on found.,")
        sb.append(" example=").append("No example found.,")
        sb.append(" thumbsDown=").append(0.toString()).append(")]")

        Assert.assertEquals(sb.toString(), dummyDefinitionList.toString())
    }
}