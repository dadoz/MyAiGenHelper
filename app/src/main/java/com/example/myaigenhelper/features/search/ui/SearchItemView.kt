package com.example.myaigenhelper.features.search.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.example.myaigenhelper.R
import com.example.myaigenhelper.ui.theme.DarkGrayTransparent
import com.example.myaigenhelper.ui.theme.MyAIGenHelperTheme
import com.example.myaigenhelper.ui.theme.White

@Composable
fun SearchItemView(
    modifier: Modifier = Modifier,
    imageResource: Int,
    title: String,
    description: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardColors(
            containerColor = DarkGrayTransparent,
            contentColor = White,
            disabledContainerColor = DarkGrayTransparent,
            disabledContentColor = White,
        ),
        shape = RoundedCornerShape(32.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(size = 64.dp)
                    .padding(end = 16.dp),
                painter = painterResource(id = imageResource),
                contentDescription = "item",
            )
            Column {
                Text(
                    modifier = Modifier,
                    fontSize = 26.sp,
                    text = title
                )
                Text(
                    modifier = Modifier,
                    text = description
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BakingScreenPreview() {
    MyAIGenHelperTheme {
        SearchItemView(
            imageResource = R.drawable.ic_gemini,
            title = "Choice Gemini as assistant",
            description = "to handle just choice the task"
        )
    }
}