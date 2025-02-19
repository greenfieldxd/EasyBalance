package com.greenfieldxd.easybalance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {

    var balance by remember { mutableStateOf(0L) }
    var amount by remember { mutableStateOf("") }

    var transactionType by remember { mutableStateOf(TransactionType.SPEND) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column (modifier = Modifier.weight(0.5f)){
            Text(
                text = "Easy Balance",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.OnBackground
            )
        }
        Column (modifier = Modifier.weight(0.5f)) {
            Text(
                text = "$balance",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomIntTextField(
                placeholder = "Enter amount",
                value = amount,
                onValueChange = { amount = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomButton(
                text = "Add Transaction",
                onClick = { balance += amount.toIntOrNull() ?: 0 },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}