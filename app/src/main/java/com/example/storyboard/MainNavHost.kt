package com.example.storyboard

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.storyboard.ui.StoryBoardViewModel
import com.example.storyboard.ui.screens.AddScreen
import com.example.storyboard.ui.screens.AddStoryScreen
import com.example.storyboard.ui.screens.HomeScreen
import com.example.storyboard.ui.screens.StoryDetailScreen

object Navigate {
    const val HOME = "HomeScreen"
    const val ADD_BOARD = "AddStoryBoardScreen"
    const val ADD_PANEL = "AddPanelScreen"
    const val STORY_DETAIL = "StoryScreen"
}

@Composable
fun MainNavHost(
    viewModel: StoryBoardViewModel = viewModel(factory = StoryBoardViewModel.Factory)
) {
    val context = LocalContext.current
    val storyState by viewModel.storyBoards.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    Scaffold { padding ->
        NavHost(
            navController,
            startDestination = Navigate.HOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(route = Navigate.HOME) {
                HomeScreen(
                    onAdd = {
                        navController.navigate(Navigate.ADD_BOARD)
                    }, storyState,
                    navController = navController
                )
            }

            composable(route = Navigate.ADD_BOARD) {
                AddStoryScreen(
                    viewModel,navController,
                    onAdd = {
                        navController.navigate(Navigate.ADD_PANEL)
                    },
                    onBack = {
                        navController.popBackStack()
                    })
            }

            composable(route = Navigate.ADD_PANEL) {
                AddScreen(onCancel = {
                    navController.popBackStack()
                }) { panel ->
                    try {
                        viewModel.addPanel(panel)
                        navController.popBackStack()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            composable(
                route = Navigate.STORY_DETAIL + "/{storyId}",
                arguments = listOf(navArgument("storyId") {
                    type = NavType.IntType
                })
            ) { navBackStackEntry ->
                val storyId: Int = navBackStackEntry.arguments?.getInt("storyId")!!
                StoryDetailScreen(
                    id = storyId, viewModel, onBack = { navController.popBackStack() },
                    navController = navController
                )
            }
        }
    }
}