package com.theapache64.reky.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.theapache64.reky.ui.theme.OuterSpace

interface ListItem {
    fun getCircleText(): String
    fun getMainText(): String
}


@Composable
fun <T : ListItem> ListItems(items: List<T>, onItemClicked: (T) -> Unit) {
    LazyColumn {
        for (item in items) {
            item {
                ListItemView(
                    item = item,
                    onUserClicked = onItemClicked
                )
            }
        }
    }
}

@Composable
private fun <T : ListItem> ListItemView(
    item: T,
    onUserClicked: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onUserClicked(item)
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circle
        Box(
            modifier = Modifier
                .size(70.dp)
                .background(OuterSpace, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.getCircleText(),
                style = MaterialTheme.typography.h5
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Name
        Text(
            text = item.getMainText(),
            style = MaterialTheme.typography.h6,
        )
    }
}
