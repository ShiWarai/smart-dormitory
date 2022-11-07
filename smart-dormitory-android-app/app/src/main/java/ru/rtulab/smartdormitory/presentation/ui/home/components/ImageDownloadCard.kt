package ru.rtulab.smartdormitory.presentation.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.ui.theme.Accent50


@Preview
@Composable
fun ImageDownloadCard(
    modifier: Modifier = Modifier,
    imageLink: String = ""
) {
    val imageSize = remember { mutableStateOf(Size.Zero) }
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        /*AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageLink)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholderimage),
            contentDescription = stringResource(R.string.image),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .onGloballyPositioned {
                    imageSize.value = it.size.toSize()
                },
        )*/
        Image(
            painter = painterResource(R.drawable.placeholderimage),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .onGloballyPositioned {
                    imageSize.value = it.size.toSize()
                },
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .size(with(LocalDensity.current) { imageSize.value.toDpSize() }),
            verticalArrangement = Arrangement.Bottom,

            ) {

            Card(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 0.dp)
                    .fillMaxWidth(),
                elevation = 0.dp,
                backgroundColor = Accent50,
                shape = RoundedCornerShape(8.dp)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(0.8f, false)
                    ) {
                        Text(
                            text = stringResource(R.string.texttodownload),
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(0.2f, false),
                        horizontalAlignment = Alignment.End
                    ) {
                        Image(
                            painter = painterResource(R.drawable.download),
                            contentDescription = stringResource(R.string.download)
                        )
                    }
                }
            }
        }
    }


}