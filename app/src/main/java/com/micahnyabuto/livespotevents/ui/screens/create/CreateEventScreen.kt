package com.micahnyabuto.livespotevents.features.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.micahnyabuto.livespotevents.core.permissions.rememberImagePicker
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    eventsViewModel: EventsViewModel=koinViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var imageUri by remember { mutableStateOf<android.net.Uri?>(null) }
    val eventTitle = remember { mutableStateOf("") }
    val eventDate = remember { mutableStateOf("") }
    val eventTime = remember { mutableStateOf("") }
    val eventLocation = remember { mutableStateOf("") }
    val eventDescription = remember { mutableStateOf("") }

    var selectedImage by remember { mutableStateOf<android.net.Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val pickImage = rememberImagePicker(
        onImagePicked = { uri -> selectedImage = uri },
        onPermissionDenied = { /* Show snackbar or toast */ }
    )



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Event",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle close */ }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = eventTitle.value,
                    onValueChange = { eventTitle.value = it },
                    label = { Text("Event Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                )

                OutlinedTextField(
                    value = eventDate.value,
                    onValueChange = { eventDate.value = it },
                    label = { Text("Date") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                )

                OutlinedTextField(
                    value = eventTime.value,
                    onValueChange = { eventTime.value = it },
                    label = { Text("Time") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                )

                OutlinedTextField(
                    value = eventLocation.value,
                    onValueChange = { eventLocation.value = it },
                    label = { Text("Location") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                )

                OutlinedTextField(
                    value = eventDescription.value,
                    onValueChange = { eventDescription.value = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 5,
                    maxLines = 10
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(vertical = 16.dp)
                        .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (selectedImage != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImage),
                            contentDescription = "Selected Event Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }else{
                        Text(
                            "Upload Image",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                        Text("Add an image to your event", fontSize = 14.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = { pickImage() },
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Upload")
                        }
                    }
                }

                Button(
                    onClick = {
                        isLoading = true
                        eventsViewModel.createEvent(
                            context = context,
                            title = eventTitle.value,
                            date = eventDate.value,
                            time = eventTime.value,
                            location = eventLocation.value,
                            description = eventDescription.value,
                            imageUri = selectedImage,
                            onSuccess = {
                                isLoading = false
                                // Clear form after successful creation
                                eventTitle.value = ""
                                eventDate.value = ""
                                eventTime.value = ""
                                eventLocation.value = ""
                                eventDescription.value = ""
                                selectedImage = null
                                // TODO: Show success message or navigate back
                            },
                            onError = { error ->
                                isLoading = false
                                // TODO: Show error message
                                error.printStackTrace()
                            }
                        )
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        text = if (isLoading) "Creating Event..." else "Create Event",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CreateEventScreenPreview() {
    CreateEventScreen()
}

