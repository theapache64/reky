package com.theapache64.reky.ui.screen.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.theapache64.reky.R
import com.theapache64.reky.data.local.model.User
import com.theapache64.reky.ui.composable.ListItems
import com.theapache64.reky.ui.composable.Loading
import com.theapache64.reky.ui.composable.PageTitle
import com.theapache64.reky.util.Resource

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:05
 */
@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    usersScrollState: LazyListState,
    onUserClicked: (User) -> Unit
) {
    Column {
        // Title
        PageTitle(stringRes = R.string.app_name)

        val usersResp by viewModel.users.collectAsState()
        when (usersResp) {
            is Resource.Loading -> {
                Loading()
            }
            is Resource.Success -> {
                val users = (usersResp as Resource.Success<List<User>>).data
                ListItems(
                    items = users,
                    onItemClicked = onUserClicked,
                    scrollState = usersScrollState
                )
            }
            is Resource.Error -> {

            }
            is Resource.Idle -> {
                // do nothing
            }
        }
    }
}
