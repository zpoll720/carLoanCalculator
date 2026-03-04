package com.example.carloancalc

import android.R
import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
    //inputs
    var price by remember { mutableStateOf("") }
    var downPayment by remember { mutableStateOf("") }
    var iRate by remember { mutableFloatStateOf(0.0f) }
//    var yearsLeft by remember { mutableStateOf("") }

    //Hoisted variable was named optionSelected
    var yearsLeft by remember { mutableStateOf("0.0") }

    //output
    var monthlyPayment by remember { mutableDoubleStateOf(0.00) }

    Column (
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.padding(10.dp).fillMaxSize()
    ) {
        Text (
            text = "Car Loan Calculator",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            modifier = Modifier.padding(bottom = 18.dp)
        )
        TextField (
            value = price,
            onValueChange = {
                price = it
            },
            label = { Text("Price of Car?") },
            singleLine = true,  //the textbox will not expand
            //the keyboard that pops up only contains numbers
            keyboardOptions = KeyboardOptions (
                keyboardType = KeyboardType.Number
            )
        )
        TextField (
            value = downPayment,
            onValueChange = {
                downPayment = it
            },
            label = { Text("Down Payment?") },
            singleLine = true,
            keyboardOptions = KeyboardOptions (
                keyboardType = KeyboardType.Number
            )
        )


        Text (
            text = "Interest Rate: "
        )
        Row (
//            modifier = modifier.padding(10.dp)
        ) {
            Slider (
                value = iRate,
                onValueChange = { it ->
                    iRate = it
                },
                valueRange = 0.00f..20.00f,
//                steps = 20
            )
        }
        iRate = "%.2f".format(iRate).toFloat()
        Text("Value: ${iRate}%")

        //timeLeft
        val radioOptions = listOf ("3", "4", "5", "6")
//        Text (
//            text = "Years Remaining: "
//        )
//        TextField(
//            value = yearsLeft,
//            onValueChange = {
//                yearsLeft = it
//            }
//        )

        //builds radio buttons
        RadioGroup(
            radioOptions = listOf("Three", "Four", "Five", "Six"),
            optionSelected = yearsLeft,
            onSelect = { yearsLeft = it}  //it is the parameter of the function which is option
        )


        //format and output variables
        monthlyPayment = "%.2f".format(monthlyPayment).toDouble()
//        Text("Price: $price")
//        Text("Down Payment: $downPayment")
//        Text("Interest Rate: $iRate")
//        Text("Years Remaining: $yearsLeft")
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Monthly Payment: ")
                }
                append("$$monthlyPayment")
            }
        )

//        var callFcn by remember { mutableStateOf(false) }

        Button (
            onClick = {
//                callFcn = true
                monthlyPayment = calcMonthlyPayment(price.toDouble(), downPayment.toDouble(), iRate/100/12, yearsLeft)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Compute")
        }
//        if (callFcn) {
//            callFcn = false
//            monthlyPayment = calcMonthlyPayment(price.toDouble(), downPayment.toDouble(), iRate/100/12, yearsLeft.toInt())
//        }
    }

}
//@Composable
//*******when all inputs are null, the screen crashes. Why?
fun calcMonthlyPayment(pr: Double, dp: Double, iR: Float, yearsString: String): Double {
//    Text("pr: $pr")
    val yearsInt = when (yearsString) {
        "Three" -> 3
        "Four" -> 4
        "Five" -> 5
        else -> 6
    }
    val months = yearsInt*12
    val monthlyPayment = iR * (pr - dp) / (1- (1+iR).pow(-months))
    return monthlyPayment
}

//In order to hoist, add 2 parameters. optionSelected and the function onSelect
@Composable                                                        //fcn is given a string a returns nothing. Simply updates a variable.
fun RadioGroup(radioOptions: List<String>, optionSelected: String, onSelect: (String) -> Unit) {
    //Problem: need this variable in CarLoanScreen function in order to pass it through the
    //calcMonthlyPayment function. This is used to tell how many years are left on the loan.
    //Solution: Hoisting. Copy and pass in needed function
//    var optionSelected by remember { mutableStateOf("4") }
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        //bad practice to have three copy and pasted rows --> so use a loop and list
        radioOptions.forEach { option ->
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.selectable (
                    selected = option == optionSelected,
                    onClick = {
//                        optionSelected = option   //before hoisting
                        onSelect(option)            //after hoisting
                    },
                    role = Role.RadioButton
                ).padding(8.dp)
            ) {
                RadioButton (
                    selected = option == optionSelected,
                    onClick = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text (
                    text = option
                )
            }
        }

    }
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