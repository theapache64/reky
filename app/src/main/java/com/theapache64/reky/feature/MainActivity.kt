package com.theapache64.reky.feature

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.theapache64.reky.R
import com.theapache64.reky.feature.splash.SplashScreen
import com.theapache64.reky.feature.users.UsersScreen
import com.theapache64.reky.ui.theme.RekyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            RekyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            SplashScreen(
                                onSplashFinished = {
                                    checkPermissionsOrFinish {
                                        navController.popBackStack()
                                        navController.navigate("users")
                                    }
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

    private fun checkPermissionsOrFinish(
        onAllPermissionGranted: () -> Unit,
    ) {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS
            )
            .withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            onAllPermissionGranted.invoke()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                R.string.main_error_permission,
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        requests: MutableList<PermissionRequest>,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }

                }
            )
            .check()
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
