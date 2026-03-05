package com.example.carloancalc;

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CarLoanViewModel : ViewModel() {
    //inputs
    var price by mutableStateOf("")
    var downPayment by mutableStateOf("")
    var iRate by mutableFloatStateOf(0.0f)

    //hoisted
    var yearsLeft by mutableStateOf("0.0")

    //output
    var monthlyPayment by mutableDoubleStateOf(0.00)
}
