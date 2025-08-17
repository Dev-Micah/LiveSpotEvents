package com.micahnyabuto.livespotevents.features.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsBaseball
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.micahnyabuto.livespotevents.R

// Data classes for events and filters
data class Event(val title: String, val imageResId: Int)

data class Filter(val name: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen() {
    // State for the search bar text
    val searchText = remember { mutableStateOf("") }

    // Dummy data for filters and events
    val filters = listOf(
        Filter("Music", Icons.Default.MusicNote),
        Filter("Sports", Icons.Default.SportsBaseball),
        Filter("Theater", Icons.Default.TheaterComedy)
    )

    // Dummy data for events. You would replace this with actual data.
    val events = listOf(
        Event("London is blue Festival", R.drawable.lights),
        Event("Friday Worship Night", R.drawable.crusade),
        Event("Club World Cup Opening", R.drawable.cwc)
    )

    Scaffold{
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                placeholder = { Text("Search", color = Color.LightGray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.LightGray) },
                trailingIcon = {
                    if (searchText.value.isNotEmpty()) {
                        IconButton(onClick = { searchText.value = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear search", tint = Color.LightGray)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // Filters
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(filters) { filter ->
                    FilterChip(
                        filter = filter,
                        onClick = { /* Handle filter click */ }
                    )
                }
            }

            // Events Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(events.size) { index ->
                    EventCard(event = events[index])
                }
            }
        }
    }
}

@Composable
fun FilterChip(filter: Filter, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text(filter.name) },
        leadingIcon = {
            Icon(
                filter.icon,
                contentDescription = filter.name,
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
        },
    )
}

@Composable
fun EventCard(event: Event) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = event.imageResId),
                contentDescription = event.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Gradient overlay at the bottom of the card for text readability
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
            )
            Text(
                text = event.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }
    }
}

