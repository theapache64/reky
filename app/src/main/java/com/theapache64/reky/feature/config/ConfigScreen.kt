package com.theapache64.reky.feature.config

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theapache64.reky.R
import com.theapache64.reky.ui.composable.PageTitle
import timber.log.Timber

/**
 * Created by theapache64 : May 30 Sun,2021 @ 16:45
 */

@Composable
fun ConfigScreen(
    recordsDir: String,
    viewModel: ConfigViewModel = hiltViewModel(),
    onPickDirectoryClicked: () -> Unit,
    onConfigFinished: () -> Unit
) {

    viewModel.recordsDir = recordsDir

    val validationErrorMsgResId by viewModel.validationError.collectAsState()
    val isConfigFinished by viewModel.isConfigFinished.collectAsState()

    if (isConfigFinished) {
        onConfigFinished.invoke()
    }

    Column {
        // Title
        PageTitle(stringRes = R.string.config_title)

        // Label
        Text(
            text = stringResource(id = R.string.config_label_records_dir),
            modifier = Modifier.padding(bottom = 5.dp)
        )


        // Value
        TextField(
            value = recordsDir,
            onValueChange = {
                // Do nothing
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Timber.d("ConfigScreen: Clicked")
                    onPickDirectoryClicked.invoke()
                },
            enabled = false
        )

        if (validationErrorMsgResId != null) {
            Text(
                text = stringResource(id = validationErrorMsgResId!!),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.subtitle2
            )
        }

        // Next
        Button(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.onFinishClicked()
            },
        ) {
            Text(text = stringResource(id = R.string.config_action_finish))
        }
    }
}


