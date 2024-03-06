package me.megaaweso.coolcodeeditor

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldBuffer
import androidx.compose.foundation.text2.input.TextFieldCharSequence
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Editor(vm: EditorViewModel = viewModel()) {
    val state = rememberSaveable(stateSaver = TextFieldState.Saver) { mutableStateOf(vm.state) }

    state.value.edit {  }
    Column {
        Box (modifier = Modifier.weight(1.0f)){
            BasicTextField2(
                state = state.value,
                inputTransformation = { originalValue, valueWithChanges ->
                    if(vm.normalMode) {
                        vm.input(valueWithChanges)
                    }
                },
                //inputTransformation = AndroidInputTransformation
            )
        }
        Button(
            modifier = Modifier.size(80.dp,  80.dp),
            onClick = { vm.toggleNormalMode() } // Replace with your desired action
        ) {
            Text("I")
        }
    }
}

//@OptIn(ExperimentalFoundationApi::class)
//object AndroidInputTransformation : InputTransformation {
//
//    override fun transformInput(
//        originalValue: TextFieldCharSequence,
//        valueWithChanges: TextFieldBuffer
//    ) {
//        Log.d("!!!", vm.normalMode.toString())
//        if(vm.normalMode) {
//            vm.input(valueWithChanges)
//            //vm.test(valueWithChanges)
//        }
//    }
//}