package com.dgioto.fairytalesinukrainian.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dgioto.fairytalesinukrainian.models.FairyTale
import com.dgioto.fairytalesinukrainian.R
import com.dgioto.fairytalesinukrainian.ui.theme.FairyTalesInUkrainianTheme

@Composable
fun MainScreen() {
    val navigateToDetail = remember { mutableStateOf(false) }
    var selectedFairytale by remember { mutableStateOf<FairyTale?>(null) }

    if (navigateToDetail.value) {
        DetailScreen(selectedFairytale!!, onBackClick = { navigateToDetail.value = false })
    } else {
        Scaffold(
            bottomBar = {
                androidx.compose.material3.NavigationBar {
                    val selectedItemPosition = remember {
                        mutableIntStateOf(0)
                    }

                    val items = listOf(
                        NavigationItem.Home,
                        NavigationItem.Favourite,
                        NavigationItem.Profile
                    )

                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemPosition.intValue == index,
                            onClick = { selectedItemPosition.intValue = index },
                            icon = {
                                Icon(item.icon, contentDescription = null)
                            },
                            label = {
                                Text(text = stringResource(id = item.titleResId))
                            }
                        )
                    }
                }
            }
        ) {
            val items = listOf(
                FairyTale(
                    image = R.drawable.koza_dereza,
                    title = "Коза дереза",
                    description = "Description 1",
                    isFavorite = false
                ),
                FairyTale(
                    image = R.drawable.telesik,
                    title = "Телесик",
                    description = "Description 2",
                    isFavorite = false
                ),
                FairyTale(
                    image = R.drawable.pan_kozkiy,
                    title = "Пан коцький",
                    description = "Description 3",
                    isFavorite = false
                ),
            )

            LazyColumn(modifier = Modifier.padding(it)) {
                items(items) { item ->
                    // Обработчик нажатия на FairyTaleItem
                    val onItemClick: () -> Unit = {
                        navigateToDetail.value = true
                        selectedFairytale = item
                    }
                    // Вызов FairyTaleItem с передачей обработчика нажатия
                    FairyTaleItem(fairyTale = item, onItemClick = onItemClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(fairyTale: FairyTale, onBackClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text(text = fairyTale.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle favorite toggle */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite, // Adjust based on favorite state
                            contentDescription = null,
                            tint = Color.Red // Adjust based on favorite state
                        )
                    }
                }
            )

            Image(
                painter = painterResource(id = fairyTale.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Text(
                text = fairyTale.description,
                modifier = Modifier.padding(all = 16.dp)
            )
        }
    }
}

@Composable
fun FairyTaleItem(fairyTale: FairyTale, onItemClick: () -> Unit) {
    val isFavoriteState = remember { mutableStateOf(fairyTale.isFavorite) }

    Card(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
    ) {
        // Обработчик нажатия добавлен к Row
        Row(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onItemClick),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                    .clip(RoundedCornerShape(15.dp))
            ) {
                Image(
                    painter = painterResource(id = fairyTale.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = fairyTale.title,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(start = 16.dp)
                    .align(alignment = Alignment.CenterVertically)
            )

            IconButton(
                onClick = { isFavoriteState.value = !isFavoriteState.value },
                modifier = Modifier.align(alignment = Alignment.Top)
            ) {
                Icon(
                    imageVector = if (isFavoriteState.value) Icons.Filled.Favorite
                    else Icons.Outlined.Favorite,
                    contentDescription = null,
                    tint = if (isFavoriteState.value) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Composable
fun LightThemePreview() {
    FairyTalesInUkrainianTheme(darkTheme = false) { MainScreen() }
}

@Preview
@Composable
fun DarkThemePreview() {
    FairyTalesInUkrainianTheme(darkTheme = true) { MainScreen() }
}