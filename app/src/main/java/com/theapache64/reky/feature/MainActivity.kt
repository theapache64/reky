package com.theapache64.reky.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.theapache64.reky.feature.splash.SplashScreen
import com.theapache64.reky.feature.users.UsersScreen
import com.theapache64.reky.ui.theme.RekyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            RekyTheme {
                // A surface container using the 'background' color from the theme
                Surface(

                ) {
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            SplashScreen(
                                onSplashFinished = {

                                    // navController.navigate("users")
                                }
                            )
                        }

                        composable("users") {
                            UsersScreen(
                                onUserClicked = {

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

/*
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RekyTheme {
        Greeting("Android")
    }
}*/
