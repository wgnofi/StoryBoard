package com.example.storyboard.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.storyboard.Navigate
import com.example.storyboard.data.StoryBoard
import com.example.storyboard.ui.BoardState

@Composable
fun HomeScreen(
    onAdd: () -> Unit,
    storyBoards: BoardState,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            Text("StoryBoard Creator", fontSize = 24.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp))
        },
        floatingActionButton = {
            FloatingActionButton(onClick =onAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "ADD")
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)) {
            when(storyBoards) {
                is BoardState.Empty -> Box(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center)) {
                    Text(
                        "Empty StoryBoard, Start Creating!",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                is BoardState.Success -> {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())) {
                        Text("Ongoing", modifier = Modifier.padding(16.dp), fontSize = 22.sp)
                        storyBoards.boards.filter { it.status == 0 }.forEach {
                            StoryCard(onClick = { id ->
                                navController.navigate(Navigate.STORY_DETAIL + "/${id}")
                            },sb = it)
                        }
                        Text("Completed", modifier = Modifier.padding(16.dp), fontSize = 22.sp)
                        storyBoards.boards.filter { it.status == 1 }.forEach {
                            StoryCard(onClick = { id ->
                                navController.navigate(Navigate.STORY_DETAIL + "/${id}")
                            },sb = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StoryCard(
    sb: StoryBoard,
    onClick: (id: Int) -> Unit
) {
    ElevatedCard(
        onClick = {
            onClick(sb.id)
        },modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                AsyncImage(
                    model = sb.panels.first().uri,
                    contentDescription = sb.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(30.dp).clip(RoundedCornerShape(10.dp))
                )
                Text(sb.name, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            }
            Text("Panels: ${sb.panels.count()}")
        }

    }
}
