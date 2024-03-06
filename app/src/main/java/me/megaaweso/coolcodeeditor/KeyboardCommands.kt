@file:OptIn(ExperimentalFoundationApi::class)

package me.megaaweso.coolcodeeditor

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldBuffer
import androidx.compose.foundation.text2.input.insert
import androidx.compose.ui.text.TextRange
import java.lang.Exception

fun applyCommand(input: String, valueWithChanges: TextFieldBuffer, insertedRange: TextRange) : Boolean{
    //val regIsNumber = Regex("^\\d+")
    //val regNotWhiteChar = Regex("[^\\s]+")
    //val regNotNumOrWhite = Regex("[^\\d\\s]+")

    //if (regNotWhiteChar.matches(input)) return

    return when(input) {
        "w" -> {
            skipToNextWord(valueWithChanges, insertedRange)
            true
        }
        "dw" -> {
            deleteWord(valueWithChanges, insertedRange)
            true
        }
        else -> false
    }
}

fun skipToNextWord(valueWithChanges: TextFieldBuffer, insertedRange: TextRange) {
    val text = valueWithChanges.toString()
    val nextLineIndex = text.indexOf('\n', insertedRange.start)
    val firstSpace = text.indexOf(' ', insertedRange.start)

    if(firstSpace < 0 || (firstSpace > nextLineIndex && nextLineIndex > -1)) return

    valueWithChanges.placeCursorBeforeCharAt(firstSpace + 1)
}

//TODO: Optimize firstSpace
//      Document
fun deleteWord(valueWithChanges: TextFieldBuffer, insertedRange: TextRange) {
    val text = valueWithChanges.toString()
    val nextLineIndex = text.indexOf('\n', insertedRange.start)
    var firstSpace = 0
    val tempIndex = text.indexOf(' ', insertedRange.start + 1)
    if(tempIndex > 0) {
        firstSpace = text.substring(0, tempIndex).lastIndexOf(' ', insertedRange.start)
    }
    else {
        firstSpace = text.substring(0, insertedRange.start).lastIndexOf(' ')
    }

    firstSpace = if(firstSpace < 0) 0 else firstSpace

    //TODO: Fix for nextlineIndex == 0
    var secondSpace = text.indexOf(' ', firstSpace + 1)
    if(secondSpace < 0) {
        secondSpace = if(nextLineIndex < 0) {
            text.length
        } else {
            nextLineIndex-1
        }
    }

    Log.d("!!!", "first: $firstSpace second: $secondSpace length: ${text.length}")

    //TODO: Move cursor if at the end of line before deleting
    if(text.get(insertedRange.start) == ' ') {
        valueWithChanges.replace(insertedRange.start, insertedRange.start + 1, "" )
    } else {
        valueWithChanges.replace(firstSpace, secondSpace, "")
    }
}