package com.theapache64.reky.feature

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.theapache64.reky.R
import com.theapache64.reky.feature.config.ConfigScreen
import com.theapache64.reky.feature.splash.SplashScreen
import com.theapache64.reky.feature.users.UsersScreen
import com.theapache64.reky.ui.theme.RekyTheme
import com.theapache64.reky.util.getAbsolutePath
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val dirPickLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val treeUri = it.data?.data
        val docUri = DocumentsContract.buildDocumentUriUsingTree(
            treeUri,
            DocumentsContract.getTreeDocumentId(treeUri)
        )
        viewModel.onDirPicked(docUri.getAbsolutePath(this)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            RekyTheme {
                NavHost(navController = navController, startDestination = "splash") {
                    // Splash Screen
                    composable("splash") {
                        SplashScreen(
                            onSplashFinished = { isConfigSet ->
                                checkPermissionsOrFinish {
                                    navController.popBackStack()
                                    if (isConfigSet) {
                                        goToUsers(navController)
                                    } else {
                                        // Need to set config
                                        navController.popBackStack()
                                        navController.navigate("config")
                                    }
                                }
                            }
                        )
                    }

                    composable("config") {

                        val recordsDir by viewModel.pickedDirectory.collectAsState()

                        ConfigScreen(
                            recordsDir = recordsDir,
                            onPickDirectoryClicked = {
                                val dirPickIntent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                                    addCategory(Intent.CATEGORY_DEFAULT)
                                }
                                dirPickLauncher.launch(dirPickIntent)
                            },
                            onConfigFinished = {
                                goToUsers(navController)
                            }
                        )
                    }

                    // Users
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

    private fun goToUsers(navController: NavHostController) {
        navController.popBackStack()
        navController.navigate("users")
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
