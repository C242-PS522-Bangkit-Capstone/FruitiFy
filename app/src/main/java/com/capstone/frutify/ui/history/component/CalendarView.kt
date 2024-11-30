package com.capstone.frutify.ui.history.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarView() {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(true) }
    val selectedDate = datePickerState.selectedDateMillis?.let {
        SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(it)
    } ?: "Select a date"

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = Color(0xFFF5F5F5),
                selectedDayContainerColor = Color(0xFF5BB85B),
                todayContentColor = Color(0xFF5BB85B),
                todayDateBorderColor = Color(0xFF5BB85B),
            ),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
                .fillMaxWidth(),
            title = {
                showDatePicker = false
            },
            headline = {
                Text(
                    text = selectedDate,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF333333)
                )
            }
        )
    }
}