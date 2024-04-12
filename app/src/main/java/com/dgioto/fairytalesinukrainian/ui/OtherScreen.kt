package com.dgioto.fairytalesinukrainian.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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

@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TextWithRadioButtons(
            text = "Тема",
            options = listOf("Світла" to true, "Темна" to false),
            onOptionSelected = { /*TODO*/ }
        )
        TextWithRadioButtons(
            text = "Мова",
            options = listOf("Укр" to true, "Eng" to false, "Рус" to false),
            onOptionSelected = { /*TODO*/ }
        )
        ColorText("Поділитись")
        ColorText("Оцінити додаток")
        ColorText("Про додаток")
        ColorText("Видалити данні")
    }
}

@Composable
fun TextWithRadioButtons(
    text: String,
    options: List<Pair<String, Boolean>>, // Список пар (текст опции, выбрана ли опция)
    onOptionSelected: (Int) -> Unit // Функция, которая будет вызвана при выборе опции
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(24.dp),
            color = MaterialTheme.colorScheme.secondary
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            options.forEachIndexed { index, (optionText, isSelected) ->
                Text(
                    text = optionText,
                    color = MaterialTheme.colorScheme.secondary
                )
                RadioButton(
                    selected = isSelected,
                    onClick = { onOptionSelected(index) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.secondary,
                        unselectedColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }
}

@Composable
fun ColorText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.secondary, // Используем необходимый цвет только здесь
        modifier = Modifier.padding(24.dp)
    )
}