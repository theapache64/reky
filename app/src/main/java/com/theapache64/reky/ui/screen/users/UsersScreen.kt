package com.theapache64.reky.ui.screen.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
        val searchKeyword by viewModel.searchKeyword.collectAsState()

        when (usersResp) {
            is Resource.Loading -> {
                Loading()
            }
            is Resource.Success -> {
                val users = (usersResp as Resource.Success<List<User>>).data

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    placeholder = {
                        Text(text = stringResource(id = R.string.hint_search))
                    },
                    value = searchKeyword,
                    onValueChange = {
                        viewModel.onSearchKeywordChanged(it)
                    }
                )


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
