package com.micahnyabuto.livespotevents.core.permissions

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

@Composable
fun rememberImagePicker(
    onImagePicked: (Uri?) -> Unit,
    onPermissionDenied: () -> Unit
): () -> Unit {
    val context = LocalContext.current

    val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> onImagePicked(uri) }
    )

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                galleryLauncher.launch("image/*")
            } else {
                onPermissionDenied()
            }
        }
    )

    // Return a lambda you can call in your button
    return remember {
        {
            val status = ContextCompat.checkSelfPermission(context, imagePermission)
            if (status == PermissionChecker.PERMISSION_GRANTED) {
                galleryLauncher.launch("image/*")
            } else {
                permissionLauncher.launch(imagePermission)
            }
        }
    }
}
