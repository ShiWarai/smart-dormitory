package ru.rtulab.smartdormitory.presentation.ui.authtorization.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rtulab.smartdormitory.R

@Preview
@Composable
fun Title(
    modifier: Modifier = Modifier
){
    Row(
        modifier=modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.smartdormitorylogo ),
            contentDescription = stringResource(id = R.string.SmartDormitory)
        )
        Column(
            Modifier.padding(start=16.dp)
        ) {
            Text(
                text = stringResource(R.string.Smart),
                fontSize = 28.sp,
                lineHeight = 28.sp,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = stringResource(R.string.Dormitory),
                fontSize = 28.sp,
                lineHeight = 28.sp,
                color = MaterialTheme.colors.primary
            )
        }
    }
}