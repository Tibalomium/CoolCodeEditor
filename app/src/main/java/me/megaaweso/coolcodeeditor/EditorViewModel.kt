package me.megaaweso.coolcodeeditor

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldBuffer
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.insert
import androidx.lifecycle.ViewModel

@ExperimentalFoundationApi
class EditorViewModel : ViewModel() {
    val state = TextFieldState()
    var normalMode = false
    private var _input = ""

    fun input(valueWithChanges: TextFieldBuffer) {
        if(normalMode && valueWithChanges.changes.changeCount > 0) {
            val insertedRange = valueWithChanges.changes.getRange(0)
//            //if(insertedRange.end == 0) return;
            _input += valueWithChanges.toString().substring(insertedRange.end - 1, insertedRange.end)
            //Log.d("!!!", _input)
//            if(_input == "w") {
//                valueWithChanges.revertAllChanges()
//                applyCommand("w", valueWithChanges, insertedRange)
//                _input = ""
//            }
            valueWithChanges.revertAllChanges()
            //Log.d("!!!", _input)
            if(applyCommand(_input, valueWithChanges, insertedRange)) {
                _input = ""
            }
        }
    }

    fun toggleNormalMode() {
        normalMode = !normalMode
        _input = ""
    }

    fun oldinput(valueWithChanges: TextFieldBuffer) {
        if(valueWithChanges.changes.changeCount > 0) {
            val insertedRange = valueWithChanges.changes.getRange(0)
            val replacedRange = valueWithChanges.changes.getOriginalRange(0)
            if(valueWithChanges.toString().contains('w')) {
                valueWithChanges.revertAllChanges()
                valueWithChanges.placeCursorBeforeCharAt(insertedRange.start + 3)
            }
            else if (replacedRange.collapsed) {
                valueWithChanges.insert(0, "bbb")
                valueWithChanges.placeCursorBeforeCharAt(0)
            }
        }
    }

    fun test(valueWithChanges: TextFieldBuffer) {
        if(valueWithChanges.changes.changeCount > 0) {
            val insertedRange =
                valueWithChanges.changes.getRange(valueWithChanges.changes.changeCount - 1)
            val replacedRange =
                valueWithChanges.changes.getOriginalRange(valueWithChanges.changes.changeCount - 1)
            if( valueWithChanges.toString().substring(insertedRange.end - 1, insertedRange.end) == "w") {
                valueWithChanges.revertAllChanges()
                valueWithChanges.insert(0, "ccc")
            }

            //Log.d("!!!", valueWithChanges.toString().substring(insertedRange.end - 1, insertedRange.end))
            Log.d("!!!", insertedRange.toString())
        }
    }
}