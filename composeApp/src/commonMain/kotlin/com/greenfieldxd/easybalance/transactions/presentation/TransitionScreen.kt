package com.greenfieldxd.easybalance.transactions.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.greenfieldxd.easybalance.transactions.data.TransactionType

class TransitionScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<TransactionScreenModel>()
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
}