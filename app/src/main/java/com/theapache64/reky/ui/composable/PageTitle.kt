package com.theapache64.reky.ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Created by theapache64 : May 31 Mon,2021 @ 02:22
 */
@Composable
fun PageTitle(
    @StringRes stringRes: Int? = null,
    string: String? = null
) {
    Text(
        text = when {
            stringRes != null -> stringResource(id = stringRes)
            string != null -> string
            else -> ""
        },
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 30.dp)
    )
}