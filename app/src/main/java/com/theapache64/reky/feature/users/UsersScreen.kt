package com.theapache64.reky.feature.users

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.theapache64.reky.R
import com.theapache64.reky.ui.composable.PageTitle

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:05
 */
@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    onUserClicked: () -> Unit
) {
    Column {
        // Title
        PageTitle(stringRes = R.string.app_name)
    }
}