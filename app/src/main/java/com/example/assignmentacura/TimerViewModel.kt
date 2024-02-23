package com.example.assignmentacura

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TimerViewModel: ViewModel() {
    private val _isCountingDown = MutableStateFlow(false)
    val isCountingDown: StateFlow<Boolean> = _isCountingDown

    private val _currentTime = MutableStateFlow(0L)
    val currentTime: StateFlow<Long> = _currentTime

    private var countdownTimerJob: Job? = null
    private var startTime: Long = 0L
    private var elapsedTimeBeforePause: Long = 0L

    fun startCustomCountDownTimer(durationInMillis: Long){
        if (!isCountingDown.value){
            _isCountingDown.value = true
            startTime = System.currentTimeMillis() - elapsedTimeBeforePause
            _currentTime.value = durationInMillis
            countdownTimerJob = viewModelScope.launch {
                while (_isCountingDown.value && _currentTime.value > 0){
                    val elapsedTime = System.currentTimeMillis() - startTime
                    _currentTime.value = durationInMillis - elapsedTime
                    delay(10)
                }
                _isCountingDown.value = false
                elapsedTimeBeforePause = 0L
            }
        }
    }

    fun pauseTimer(){
        if (isCountingDown.value) {
            _isCountingDown.value = false
            elapsedTimeBeforePause = System.currentTimeMillis() - startTime
            countdownTimerJob?.cancel()
        }
    }

    fun stopTimer(){
        _isCountingDown.value = false
        countdownTimerJob?.cancel()
        _currentTime.value = 0
        elapsedTimeBeforePause = 0L
    }
}

interface TimerListener{
    fun onTimerEnd()
}