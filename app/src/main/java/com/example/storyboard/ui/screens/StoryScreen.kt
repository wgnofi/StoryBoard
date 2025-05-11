package com.example.storyboard.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.storyboard.common.PanelCard
import com.example.storyboard.ui.StoryBoardViewModel

@Composable
fun StoryDetailScreen(
    id: Int,
    viewModel: StoryBoardViewModel,
    navController: NavHostController,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getStoryBoard(id)
    }
    val context = LocalContext.current
    val storyBoard by viewModel.selectedBoard.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(
                onClick = onBack
            ) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }
            Text(storyBoard?.name ?: "No name")
            IconButton(onClick = {
                viewModel.deleteStoryBoard(storyBoard!!)
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
            storyBoard?.panels?.forEach {
                PanelCard(it)
            }
            if (storyBoard?.status == 0) {
                ElevatedButton(onClick = {
                    viewModel.updateStoryBoard(storyBoard!!.copy(status = 1))
                    Toast.makeText(context, "Marked as Completed", Toast.LENGTH_SHORT).show()
                }, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text("Mark as Completed")
                }
            }
        }
    }
}
