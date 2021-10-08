package com.hardiksachan.mvvmtodolist.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.hardiksachan.mvvmtodolist.ui.nav.NavigationComponent
import com.hardiksachan.mvvmtodolist.ui.nav.Navigator
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val navigator = remember {
                    Navigator()
                }
                NavigationComponent(
                    navController = navController,
                    navigator = navigator
                )
            }
        }
    }
}