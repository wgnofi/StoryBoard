package com.example.storyboard.ui.screens

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.storyboard.R
import com.example.storyboard.common.PanelCard
import com.example.storyboard.data.Panel
import com.example.storyboard.data.StoryBoard
import com.example.storyboard.ui.StoryBoardViewModel


@Composable
fun AddStoryScreen(
    viewModel: StoryBoardViewModel,
    navController: NavHostController,
    onAdd: () -> Unit,
    onBack: () -> Unit
) {
    val panels by viewModel.panels.collectAsStateWithLifecycle()
    var name by rememberSaveable {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text("Create StoryBoard")
            }
            OutlinedButton(onClick = {
                viewModel.createStoryBoard(StoryBoard(name = name, panels = panels))
                navController.popBackStack()
            }, enabled = name.isNotEmpty() && panels.isNotEmpty()) {
                Text("Create")
            }
        }
        OutlinedTextField(value = name, onValueChange = {name = it}, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            label = { Text("Enter name of storyboard") },
            supportingText = {
                Text("You can create Board if you fill the name and having at-least a panel", fontSize = 10.sp)
            })
        ElevatedButton(onClick = onAdd, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text("Add Panels")
        }
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        Text("Panels", modifier = Modifier.padding(start = 16.dp), fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        if (panels.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)) {
                Text("Add some panels", fontSize = 12.sp)
            }
        } else {
            Column(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())) {
                panels.forEach {
                    PanelCard(it)
                }
            }
        }

    }
}


@Composable
fun AddScreen(
    onCancel: () -> Unit,
    onAdd:(p: Panel) -> Unit
) {
    val context = LocalContext.current
    var selectedImageUri by rememberSaveable {
        mutableStateOf<android.net.Uri?>(null)
    }
    var scene by rememberSaveable {
        mutableStateOf("")
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> uri?.let {
            selectedImageUri = it
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }}
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onCancel) {
                Text("cancel")
            }
            TextButton(onClick = {
                onAdd(Panel(selectedImageUri.toString(), scene))
            },
                enabled = selectedImageUri != null && scene.isNotEmpty()) {
                Text("Add")
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Surface(color = Color.DarkGray, shape = RoundedCornerShape(10.dp), shadowElevation = 3.dp,
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                if (selectedImageUri != null) {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.upload), contentDescription = "Upload",
                        contentScale = ContentScale.Inside
                    )
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            OutlinedTextField(value = scene,
                onValueChange = {scene = it},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                label = {
                    Text("Describe the scene here")
                },
                enabled = selectedImageUri != null
            )

        }
    }
}
