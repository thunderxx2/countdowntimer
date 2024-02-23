package com.example.assignmentacura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assignmentacura.ui.theme.AssignmentAcuraTheme
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssignmentAcuraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    CustomCountdownTimer()
                }
            }
        }
    }
}

@Composable
fun CustomCountdownTimer() {
    val timerViewModel: TimerViewModel = koinViewModel()

    val isCountdownRunning by timerViewModel.isCountingDown.collectAsState()
    val currentTime by timerViewModel.currentTime.collectAsState()

    val minutes = TimeUnit.MILLISECONDS.toMinutes(currentTime)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(currentTime) % 60
    val milliSeconds = currentTime % 1000

    val startOrPauseButtonText = if (isCountdownRunning) "Pause" else "Start"

    Box(modifier = Modifier.size(250.dp)) {
        CircularProgressIndicator(
            progress = 1f - (currentTime.toFloat() / 60000),
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = String.format("%02d:%02d.%03d", minutes, seconds, milliSeconds),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(modifier = Modifier.padding(8.dp)) {
                Button(
                    onClick = {
                              if (isCountdownRunning)
                                  timerViewModel.pauseTimer()
                              else
                                  timerViewModel.startCustomCountDownTimer(60000)
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(90.dp)
                ) {
                    Text(text = startOrPauseButtonText)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                              timerViewModel.stopTimer()
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(90.dp)
                ) {
                    Text(text = "Stop")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CustomCountdownTimerPreview() {

    Box(modifier = Modifier.size(250.dp)) {
        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier.matchParentSize()
        )

        Column(modifier = Modifier
            .padding(8.dp)
            .align(Alignment.Center)) {
            Text(text = "00:000", modifier = Modifier.align(Alignment.CenterHorizontally))

            Row(modifier = Modifier.padding(8.dp)) {
                Button(onClick = {}, shape = CircleShape, modifier = Modifier.size(90.dp)) {
                    Text(text = "Pause")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {}, shape = CircleShape, modifier = Modifier.size(90.dp)) {
                    Text(text = "Stop")
                }
            }
        }
    }
}