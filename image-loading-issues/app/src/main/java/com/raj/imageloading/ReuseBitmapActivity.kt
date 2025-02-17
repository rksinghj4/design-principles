package com.raj.imageloading

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.raj.imageloading.ui.theme.ImageLoadingIssuesTheme

class ReuseBitmapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageLoadingIssuesTheme {
                Surface {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        /*val nextBitmap = BitmapFactory.decodeResource(
                            LocalContext.current.resources,
                            R.drawable.next
                        )
                        SetBitmapOnImage(nextBitmap)*/

                        val options = BitmapFactory.Options()
                        val reuseBitMap =
                            (application as ImageLoadingApplication).getBitMapFromPool(200, 200)
                        /**
                         * To show bitmap with Same image as saved in MainActivity then just use it like below
                         */
//                        reuseBitMap?.let {
//                            SetBitmapOnImage(it)
//                        }

                        /**
                         * options.inMutable = true while saving in MainActivity
                         * To inflate new image in same bit map here
                         * options.inBitmap = reuseBitMap//reuse bitmap
                         */
                        options.inBitmap = reuseBitMap//reuse bitmap
                        val decodeNewImageInReusableBitMap =
                            BitmapFactory.decodeResource(
                                LocalContext.current.resources,
                                R.drawable.next,//new image
                                options//in same bitmap
                            )

                        decodeNewImageInReusableBitMap?.let {
                            SetBitmapOnImage(decodeNewImageInReusableBitMap)
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, ReuseBitmapActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}