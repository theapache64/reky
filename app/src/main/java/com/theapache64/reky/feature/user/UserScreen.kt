package com.theapache64.reky.feature.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.theapache64.reky.R
import com.theapache64.reky.data.local.model.Recording
import com.theapache64.reky.ui.composable.ListItems
import com.theapache64.reky.ui.composable.Loading
import com.theapache64.reky.ui.composable.PageTitle
import com.theapache64.reky.util.Resource

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:13
 */
@Composable
fun UserScreen(
    viewModel: UserViewModel = hiltViewModel(),
    onRecordingClicked: (Recording) -> Unit
) {

    Column {

        val pageTitle by viewModel.pageTitle.collectAsState()

        // Title
        PageTitle(string = pageTitle)

        val recordings by viewModel.recordings.collectAsState()

        when (recordings) {
            is Resource.Idle -> {
                // do nothing
            }
            is Resource.Loading -> {
                Loading()
            }
            is Resource.Success -> {
                val items = (recordings as Resource.Success<List<Recording>>).data
                ListItems(
                    items = items,
                    onItemClicked = onRecordingClicked
                )
            }
            is Resource.Error -> {
                Text(text = "Something went wrong: ${(recordings as Resource.Error<List<Recording>>).errorData}")
            }
        }
    }
}