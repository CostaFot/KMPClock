package com.markedasduplicate.kmpclock.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ClockViewModel : ViewModel() {

    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    private val _time = MutableStateFlow(LocalTime.now().format(formatter))
    val time: StateFlow<String> = _time

    init {
        viewModelScope.launch {
            while (true) {
                delay((60 - LocalTime.now().second) * 1000L)
                _time.value = LocalTime.now().format(formatter)
            }
        }
    }
}
