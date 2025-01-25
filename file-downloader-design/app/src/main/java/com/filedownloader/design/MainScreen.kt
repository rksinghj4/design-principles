package com.filedownloader.design

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    progress: IntState,
    downloadStatus: State<String>,
    onClickedStatus: (status: Status) -> Unit
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ShowProgress(progress, downloadStatus)

        SetupUI(onClickedStatus)
    }
}


@Composable
fun ShowProgress(
    progress: IntState,
    downloadStatus: State<String>,
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LinearProgressIndicator(progress = progress.intValue.toFloat()/10)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${progress.intValue}%"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = downloadStatus.value)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SetupUI(
    onClickedStatus: (status: Status) -> Unit
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Button(onClick = {
            onClickedStatus(Status.START)
        }) {

            Text(text = "Start")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onClickedStatus(Status.PAUSE)
        }) {
            Text(text = "Pause")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onClickedStatus(Status.CANCEL)
        }) {
            Text(text = "Cancel")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onClickedStatus(Status.CANCEL_ALL)
        }) {
            Text(text = "Cancel all")
        }
    }
}