package com.capstone.frutify.ui.home.scan

import android.Manifest
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.capstone.frutify.ui.home.scan.component.CameraPreview
import com.capstone.frutify.utils.FruitClassifierHelper
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    fruitName: String,
    onBackClicked: () -> Unit,
    onScanCompleted: (scanResult: String, imageUri: String) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var isPermissionGranted by remember { mutableStateOf(false) }

    val imageCapture = remember { ImageCapture.Builder().build() }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start= 16.dp,end=16.dp, top = 40.dp, bottom = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        onBackClicked()
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = Color(0xFF333333),
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                Text(
                    text = fruitName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF333333)
                )
                Icon(
                    Icons.Rounded.MoreVert,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Camera preview
                if (isPermissionGranted) {
                    CameraPreview(
                        onUseCamera = { previewView, cameraProvider ->
                            val preview = Preview.Builder().build()
                            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                            preview.surfaceProvider = previewView.surfaceProvider
                            try {
                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    preview,
                                    imageCapture
                                )
                            } catch (e: Exception) {
                                Toast.makeText(context, "Failed to start camera: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }else {
                    RequestCameraPermission {
                        isPermissionGranted = true
                    }
                }

                Button(
                    onClick = {
                        val photoFile = File(
                            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            "IMG_${System.currentTimeMillis()}.jpg"
                        )
                        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                        imageCapture.takePicture(
                            outputOptions,
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                    val uri = Uri.fromFile(photoFile)
                                    val result = FruitClassifierHelper(context).classifyFruit(uri, fruitName)
                                    result?.let { (label, confidence) ->
                                        Log.d("Scan Screen", "Result: $label with confidence: $confidence")
                                        onScanCompleted(label, uri.toString())
                                    } ?: run {
                                        Toast.makeText(context, "Failed to classify image", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    Toast.makeText(context, "Image capture failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5BB85B),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "Scan",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission(
    onPermissionGranted: () -> Unit
) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            onPermissionGranted()
        }
    }

    if (!permissionState.status.isGranted) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Camera permission is required for scanning.")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { permissionState.launchPermissionRequest() }) {
                Text("Grant Permission")
            }
        }
    }
}
