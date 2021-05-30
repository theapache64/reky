package com.theapache64.reky.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.theapache64.reky.R

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:17
 */
@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onSplashFinished: (isConfigSet: Boolean) -> Unit
) {

    val isSplashFinished by viewModel.isSplashFinished.collectAsState()

    if (isSplashFinished) {
        onSplashFinished(viewModel.isConfigSet)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        // Logo
        Image(painter = painterResource(id = R.drawable.ic_logo), contentDescription = "Logo")

        // Version
        Text(
            text = viewModel.appVersion,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}

