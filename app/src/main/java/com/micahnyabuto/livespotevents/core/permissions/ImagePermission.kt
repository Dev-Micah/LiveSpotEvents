package com.micahnyabuto.livespotevents.core.permissions

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

@Composable
fun RememberImagePickerPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current

    // Determine the right permission based on Android version
    val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Launcher to request permission
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onPermissionGranted() else onPermissionDenied()
    }

    // Function to check and request permission
    fun requestPermission() {
        val permissionStatus = ContextCompat.checkSelfPermission(context, imagePermission)
        if (permissionStatus == PermissionChecker.PERMISSION_GRANTED) {
            onPermissionGranted()
        } else {
            launcher.launch(imagePermission)
        }
    }

    // Return the function so caller can trigger it
    LaunchedEffect(Unit) {
        requestPermission()
    }
}
