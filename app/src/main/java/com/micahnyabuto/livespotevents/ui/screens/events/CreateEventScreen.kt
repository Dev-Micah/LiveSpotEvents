package com.micahnyabuto.livespotevents.ui.screens.events

import android.net.Uri
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.micahnyabuto.livespotevents.core.permissions.rememberImagePicker
import com.micahnyabuto.livespotevents.ui.components.CustomButton
import com.micahnyabuto.livespotevents.ui.components.CustomTextField
import com.micahnyabuto.livespotevents.ui.components.SuccessAnimation
import com.micahnyabuto.livespotevents.utils.DateDialog
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    navController: NavController,
    eventsViewModel: EventsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by eventsViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val eventTitle = remember { mutableStateOf("") }
    val eventDate = remember { mutableStateOf("") }
    val eventTime = remember { mutableStateOf("") }
    val eventLocation = remember { mutableStateOf("") }
    val eventDescription = remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val showDatePicker = remember { mutableStateOf(false) }
    var showSuccessAnimation by remember { mutableStateOf(false) }


    val pickImage = rememberImagePicker(
        onImagePicked = { uri -> selectedImage = uri },
        onPermissionDenied = { /* Show snackbar or toast */ }
    )

    LaunchedEffect(uiState) {
        if (uiState.createError != null) {
            snackbarHostState.showSnackbar(
                message = uiState.createError!!,
                actionLabel = "Dismiss"
            )
            eventsViewModel.resetCreateEventState()
        }

        if (!uiState.isCreating && uiState.createError == null && eventTitle.value.isNotEmpty()) {
            showSuccessAnimation = true
            delay(2000) // Show animation for 2 seconds
            navController.popBackStack()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            eventsViewModel.resetCreateEventState()
        }
    }

    if (showDatePicker.value) {
        DateDialog(
            onDateSelected = {
                eventDate.value = it
                showDatePicker.value = false
            },
            onDismiss = {
                showDatePicker.value = false
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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

                )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (showSuccessAnimation) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    SuccessAnimation()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    CustomTextField(
                        value = eventTitle.value,
                        onValueChange = { eventTitle.value = it },
                        label = "Event Title"
                    )

                    CustomTextField(
                        value = eventDate.value,
                        onValueChange = { eventDate.value = it },
                        label = "Date",
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker.value = true }) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "Pick Date"
                                )
                            }
                        }
                    )

                    CustomTextField(
                        value = eventTime.value,
                        onValueChange = { eventTime.value = it },
                        label = "Time"
                    )

                    CustomTextField(
                        value = eventLocation.value,
                        onValueChange = { eventLocation.value = it },
                        label = "Location"
                    )

                    CustomTextField(
                        value = eventDescription.value,
                        onValueChange = { eventDescription.value = it },
                        label = "Description",
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(vertical = 16.dp)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(12.dp)
                            )
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
                        } else {
                            Text(
                                "Upload Image",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "Add an image to your event",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            OutlinedButton(
                                onClick = { pickImage() },
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text("Upload")
                            }
                        }
                    }

                    CustomButton(
                        onClick = {
                            eventsViewModel.createEvent(
                                context = context,
                                title = eventTitle.value,
                                date = eventDate.value,
                                time = eventTime.value,
                                location = eventLocation.value,
                                description = eventDescription.value,
                                imageUri = selectedImage,
                            )
                        },
                        enabled = !uiState.isCreating
                    ) {
                        if (uiState.isCreating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Create Event")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateEventScreenPreview() {
    CreateEventScreen(navController = rememberNavController())
}
