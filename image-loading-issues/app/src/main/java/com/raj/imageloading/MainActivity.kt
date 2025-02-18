package com.raj.imageloading

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raj.imageloading.bitmapcache.decodeImageFromPath
import com.raj.imageloading.ui.theme.ImageLoadingIssuesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        decodeImageFromPath()

        setContent {

            ImageLoadingIssuesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen(
                        this,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun Screen(fromActivity: Activity, modifier: Modifier) {
        val options = BitmapFactory.Options()
        /**
         * Test it with options.inMutable = true/false - by default it is false
         */
        options.inMutable = true//Now it became mutable - means it can be reused

        val mainBitmap =
            BitmapFactory.decodeResource(
                fromActivity.resources,
                R.drawable.main,
                options
            )
        Log.d("mainBitmap", "mainBitmap.byteCount ${mainBitmap.byteCount}")

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SetBitmapOnImage(mainBitmap)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    SecondActivity.show(fromActivity)
                    //options.inMutable = false//Now it became immutable - means it can't be reused
                    fromActivity.finish()
                }
            ) {
                Text(text = "Next")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    ReuseBitmapActivity.show(fromActivity)
                    (fromActivity.applicationContext as ImageLoadingApplication).addToBitmapPool(
                        mainBitmap
                    )//To reuse it later
                    fromActivity.finish()
                }
            ) {
                Text(text = "Reuse same bitmap")
            }
        }
    }
}


@Composable
fun SetBitmapOnImage(bitmap: Bitmap) {
    Image(bitmap = bitmap.asImageBitmap(), contentDescription = "image_bitmap")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImageLoadingIssuesTheme {
    }
}