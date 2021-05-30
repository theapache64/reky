package com.theapache64.reky.feature.users

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:05
 */
@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    onUserClicked: () -> Unit
) {
    Text(text = "$viewModel")
}