package com.greenfieldxd.easybalance.presentation.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.greenfieldxd.easybalance.domain.CategoryModel
import com.greenfieldxd.easybalance.domain.TransactionModel
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.ChangeTransactionTypeButton
import com.greenfieldxd.easybalance.presentation.CustomButton
import com.greenfieldxd.easybalance.presentation.CustomTextField

class EditTransitionScreen(val id: Long) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<EditTransitionScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        val categories by screenModel.categories.collectAsState(emptyList())
        val transaction by screenModel.transactionState.collectAsState()

        LaunchedEffect(id) {
            screenModel.loadTransaction(id)
        }

        transaction?.let { transactionModel ->
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (categories.isNotEmpty()) {
                    EditTransactionItem(
                        transaction = transactionModel,
                        categories = categories,
                        onSave = {
                            screenModel.updateTransaction(it)
                            navigator.pop()
                        },
                        onCancel = { navigator.pop() }
                    )
                }
            }
        } ?: run {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Транзакция загружается...", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun EditTransactionItem(
    modifier: Modifier = Modifier,
    categories: List<CategoryModel>,
    transaction: TransactionModel,
    onSave: (TransactionModel) -> Unit,
    onCancel: () -> Unit
) {
    var amount by remember { mutableStateOf(transaction.amount.toString()) }
    var category by remember { mutableStateOf(transaction.category) }
    var description by remember { mutableStateOf(transaction.description) }
    var transactionType by remember { mutableStateOf(transaction.transactionType) }

    Column(
        modifier = modifier.background(color = AppColors.Surface, MaterialTheme.shapes.medium).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Редактирование транзакции",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        ChangeTransactionTypeButton(transactionType, onTypeChange = { transactionType = it })
        CategoryPicker(initCategory = category, categories = categories, onSelected = { category = it.name })
        CustomTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = "Описание",
            modifier = Modifier.fillMaxWidth()
        )
        CustomTextField(
            value = amount,
            onValueChange = { amount = it },
            placeholder = "Сумма",
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomButton(
                modifier = Modifier.weight(0.5f),
                text = "Сохранить",
                onClick = {
                    val updatedTransaction = TransactionModel(
                        id = transaction.id,
                        amount = amount.toDoubleOrNull() ?: transaction.amount,
                        category = category,
                        description = description,
                        date = transaction.date,
                        transactionType = transactionType
                    )
                    onSave(updatedTransaction)
                }
            )
            CustomButton(
                modifier = Modifier.weight(0.5f),
                text = "Отмена",
                backgroundColor = AppColors.Red,
                onClick = { onCancel() }
            )
        }
    }
}

@Composable
expect fun CategoryPicker(initCategory: String, categories: List<CategoryModel>, onSelected: (CategoryModel) -> Unit)