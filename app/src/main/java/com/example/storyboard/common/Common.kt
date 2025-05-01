package com.example.storyboard.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.storyboard.R
import com.example.storyboard.data.Panel

@Composable
fun PanelCard(panel: Panel) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start) {
            Surface(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                AsyncImage(
                    model = panel.uri,
                    contentDescription = panel.scene,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(shape = RoundedCornerShape(10.dp))
                )
            }
            Text(panel.scene, fontSize = 14.sp)
        }
    }
}
