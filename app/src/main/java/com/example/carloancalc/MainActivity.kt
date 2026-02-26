package com.example.carloancalc

import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carloancalc.ui.theme.CarLoanCalcTheme
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarLoanCalcTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CarLoanScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CarLoanScreen(modifier: Modifier = Modifier) {
    var price by remember { mutableStateOf("") }
    var downPayment by remember { mutableStateOf("") }
    var iRate by remember { mutableFloatStateOf(0.0f) }
    var yearsLeft by remember { mutableStateOf("") }
    var monthlyPayment by remember { mutableDoubleStateOf(0.0) }

    Column (
        modifier = modifier.padding(15.dp)
    ) {
        Text(
            text = "Price of Car: "
        )
        TextField (
            value = price,
            onValueChange = {
                price = it
            }
        )

        Text (
            text = "Down Payment Paid: "
        )
        TextField (
            value = downPayment,
            onValueChange = {
                downPayment = it
            }
        )

        Row (
            modifier = modifier.padding(10.dp)
        ) {
            Text (
                text = "Interest Rate: "
            )
            Slider (
                value = iRate,
                onValueChange = { it ->
                    iRate = it
                },
                valueRange = 0.00f..20.00f,
//                steps = 100
            )
        }
        Text("Value: ${iRate}%")

        //timeLeft
        val radioOptions = listOf ("3", "4", "5", "6")
        Text (
            text = "Years Remaining: "
        )
        TextField(
            value = yearsLeft,
            onValueChange = {
                yearsLeft = it
            }
        )
//        RadioButton(
//
//            onClick = (() --> Unit)
//        )
        RadioButton(

        )

        Text("Price: $price")
        Text("Down Payment: $downPayment")
        Text("Interest Rate: $iRate")
        Text("Years Remaining: $yearsLeft")
        Text("Monthly Payment: $monthlyPayment")
        var callFcn by remember { mutableStateOf(false) }
        Button (
            onClick = {
                callFcn = true
//                monthlyPayment = calcMonthlyPayment(price.toDouble(), downPayment.toDouble(), iRate/100/12, yearsLeft.toInt())
            }
        ) {
            Text("Compute")
        }
        if (callFcn) {
            callFcn = false
            monthlyPayment = calcMonthlyPayment(price.toDouble(), downPayment.toDouble(), iRate/100/12, yearsLeft.toInt())
        }
    }

}
//@Composable
fun calcMonthlyPayment(pr: Double, dp: Double, iR: Float, years: Int): Double {
//    Text("pr: $pr")
    val months = years*12
    val monthlyPayment = iR * (pr - dp) / (1- (1+iR).pow(-months))
    return monthlyPayment
}

//a good attempt
//@Composable
//fun buildInputBox (x: Double) {
//    Text (
//        text = "Price: "
//    )
//    TextField (
////        value = x,
////        onValueChange = {
////            x = it
////        }
//        value = x,
//        onValueChange = {
//            x = it
//        }
//    )
//}