package com.dgioto.fairytalesinukrainian.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dgioto.fairytalesinukrainian.models.FairyTale

@Composable
fun HomeScreen(
    navController: NavHostController,
    items: List<FairyTale>,
    viewModel: MainScreenViewModel
) {
    LazyColumn(modifier = Modifier.padding()) {
        items(items) { item ->
            FairyTaleItem(navController, item, viewModel)
        }
    }
}

@Composable
fun FavouriteScreen(navController: NavHostController, viewModel: MainScreenViewModel) {
    LazyColumn(modifier = Modifier.padding()) {
        items(viewModel.favoriteFairyTales) { fairyTale ->
            FairyTaleItem(
                navController = navController,
                fairyTale = fairyTale,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { Text("Profile  Screen") }
}