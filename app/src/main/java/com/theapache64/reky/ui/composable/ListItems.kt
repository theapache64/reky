package com.theapache64.reky.ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
fun <T : ListItem> ListItems(
    scrollState: LazyListState,
    items: List<T>,
    onItemClicked: (T) -> Unit,
    onItemLongClicked: ((T) -> Unit)? = null
) {
    LazyColumn(
        state = scrollState
    ) {
        for (item in items) {
            item {
                ListItemView(
                    item = item,
                    onItemClicked = onItemClicked,
                    onItemLongClicked = onItemLongClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun <T : ListItem> ListItemView(
    modifier: Modifier = Modifier,
    item: T,
    onItemClicked: (T) -> Unit,
    onItemLongClicked: ((T) -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onItemClicked(item)
                },
                onLongClick = {
                    onItemLongClicked?.invoke(item)
                }
            )
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
