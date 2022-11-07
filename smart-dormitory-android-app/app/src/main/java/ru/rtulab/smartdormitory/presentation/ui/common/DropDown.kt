package ru.rtulab.smartdormitory.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import ru.rtulab.smartdormitory.ui.theme.Black
import ru.rtulab.smartdormitory.ui.theme.White

@Preview
@Composable
fun DropDown(
    modifier: Modifier = Modifier,
    label: String = "label",
    array: List<String> = listOf(
        "Delhi",
        "Mumbai",
        "Chennai",
        "Kolkata",
        "Hyderabad",
        "Bengaluru",
        "Pune"
    ),
    seltext: String,
    selTextfun: (String) -> Unit
) {

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded = remember { mutableStateOf(false) }

    // Create a list of cities

    // Create a string value to store the selected city

    var mTextFieldSize = remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded.value)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(modifier.padding(vertical = 8.dp)) {

        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(

            value = seltext,
            onValueChange = { selTextfun(it) },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize.value = coordinates.size.toSize()
                },
            label = { Text(text = label) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded.value = !mExpanded.value })
            },
            shape = RoundedCornerShape(8.dp),
            readOnly = true
        )

        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = mExpanded.value,
            onDismissRequest = { mExpanded.value = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.value.width.toDp() })
                .background(White)
        ) {
            array.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selTextfun(label)
                        mExpanded.value = false
                    },
                ) {
                    Text(
                        text = label,
                        color = Black,
                    )
                }
            }
        }
    }
}