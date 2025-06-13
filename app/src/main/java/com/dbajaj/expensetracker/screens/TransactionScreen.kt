package com.dbajaj.expensetracker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.material3.VerticalDivider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.dbajaj.expensetracker.R
import com.dbajaj.expensetracker.RetrofitClient
import com.dbajaj.expensetracker.TokenManager
import com.dbajaj.expensetracker.data.Transaction
import com.dbajaj.expensetracker.data.TransactionDTO
import com.dbajaj.expensetracker.ui.theme.BlackGray
import com.dbajaj.expensetracker.ui.theme.OffWhite
import com.dbajaj.expensetracker.viewModel.TransactionViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun TransactionScreen(transactionViewModel: TransactionViewModel = viewModel()) {
    val transactions = transactionViewModel.transactions
    val scope=rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {TopBar(
            onConfirm = {
                scope.launch {
                    RetrofitClient.
                    createTransaction(TokenManager.getToken(context)?:"").
                    addTransaction(it)
                }
            }
        )},
        bottomBar = {BottomBar()},
        containerColor = if(isSystemInDarkTheme())
            BlackGray else OffWhite
    ){
        innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(transactions) {
                Box(modifier = Modifier.padding(8.dp)) {
                    TransactionItem(transaction = it)
                }
            }
        }
    }
}

@Composable
fun BottomBar() {
    Column {
        HorizontalDivider(thickness = 1.dp, color =
            if(isSystemInDarkTheme())Color.White else Color.Black)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Text("Total Expenses: ", fontSize = 17.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(start=8.dp))
            VerticalDivider(thickness = 1.dp, color =
                if(isSystemInDarkTheme())Color.White else Color.Black,
                modifier = Modifier.height(30.dp))
            Text("Total Income: ", fontSize = 17.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(end=8.dp))
        }
    }
}

@Composable
fun TopBar(modifier: Modifier=Modifier,onConfirm: (TransactionDTO) -> Unit){
    var showDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier) {
        Row(modifier=modifier.padding(top=45.dp,start=8.dp,end=8.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Transactions", fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add", modifier = Modifier.clickable{
                    showDialog=true
                }
            )

        }
        HorizontalDivider(thickness = 1.dp, color =
            if(isSystemInDarkTheme())Color.White else Color.Black)
        if(showDialog){
            AddTransactionDialog(
                onDismiss = { showDialog = false },
                onConfirm = {
                    onConfirm(it)
                    showDialog = false
                }
            )
        }
    }

}

@Composable
fun AddTransactionDialog(
    onDismiss: () -> Unit,
    onConfirm: (TransactionDTO) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("INCOME") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Transaction") },
        text = {
            Column {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                Row {
                    RadioButton(selected = type == "INCOME", onClick = { type = "INCOME" })
                    Text("Income")
                    Spacer(Modifier.width(16.dp))
                    RadioButton(selected = type == "EXPENSE", onClick = { type = "EXPENSE" })
                    Text("Expense")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val dto = TransactionDTO(
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    description = description,
                    type = type
                )
                onConfirm(dto)
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun TransactionItem(transaction: Transaction){
    ElevatedCard(elevation = CardDefaults.cardElevation(5.dp),modifier = Modifier.fillMaxWidth()){
        Column(modifier = Modifier.padding(8.dp)) {
            Text(transaction.amount.toString(), fontSize = 17.sp, fontWeight = FontWeight.Bold)
            if(transaction.description!="")Text(transaction.description, fontSize = 12.sp)
            Text(transaction.date.toString(), fontSize = 12.sp)
        }

    }
}