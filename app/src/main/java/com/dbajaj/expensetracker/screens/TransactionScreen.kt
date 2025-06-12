package com.dbajaj.expensetracker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.material3.VerticalDivider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.dbajaj.expensetracker.R
import com.dbajaj.expensetracker.ui.theme.BlackGray
import com.dbajaj.expensetracker.ui.theme.OffWhite

@Composable
fun TransactionScreen() {
    Scaffold(
        topBar = {TopBar()},
        bottomBar = {BottomBar()},
        containerColor = if(isSystemInDarkTheme())
            BlackGray else OffWhite
    ){
        innerPadding ->
        Text("Transactions List", modifier = Modifier
            .padding(innerPadding)
            .padding(start = 8.dp))
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
fun TopBar(modifier: Modifier=Modifier){
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
    }

}

@Preview
@Composable
fun TransactionScreenPreview(){
    TransactionScreen()
}