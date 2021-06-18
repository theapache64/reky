package com.theapache64.reky.feature.users

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theapache64.reky.R
import com.theapache64.reky.data.local.model.User
import com.theapache64.reky.ui.composable.PageTitle
import com.theapache64.reky.ui.theme.OuterSpace

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:05
 */
@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    onUserClicked: (User) -> Unit
) {
    Column {
        // Title
        PageTitle(stringRes = R.string.app_name)

        val users by viewModel.users.collectAsState()
        Users(users, onUserClicked)
    }
}

@Composable
private fun Users(users: List<User>, onUserClicked: (User) -> Unit) {
    LazyColumn() {
        for (user in users) {
            item {
                UserItem(
                    user = user,
                    onUserClicked = onUserClicked
                )
            }
        }
    }
}

@Composable
private fun UserItem(
    user: User,
    onUserClicked: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onUserClicked(user)
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
                text = "${user.recordCount ?: 0}",
                style = MaterialTheme.typography.h5
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Name
        Text(
            text = user.contact.name,
            style = MaterialTheme.typography.h6,
        )
    }
}
