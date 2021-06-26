package com.theapache64.reky.ui.screen.config

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
    onFileAnIssueClicked: () -> Unit,
    onConfigFinished: () -> Unit
) {

    viewModel.recordsDir = recordsDir

    val validationErrorMsgResId by viewModel.validationError.collectAsState()
    val isConfigFinished by viewModel.isConfigFinished.collectAsState()
    val isUnsupportedDevice by viewModel.isUnsupportedDevice.collectAsState()

    if (isConfigFinished) {
        onConfigFinished.invoke()
    }

    if (isUnsupportedDevice) {
        FileAnIssue(onFileAnIssueClicked)
    } else {
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


}

@Composable
private fun FileAnIssue(onFileAnIssueClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_warning),
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 10.dp),
            contentDescription = ""
        )

        Text(
            text = stringResource(id = R.string.config_unsupported_device),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Button(onClick = {
            onFileAnIssueClicked.invoke()
        }) {
            Text(text = stringResource(id = R.string.action_file_an_issue))
        }
    }
}


