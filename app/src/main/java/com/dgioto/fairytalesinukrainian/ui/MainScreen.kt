package com.dgioto.fairytalesinukrainian.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dgioto.fairytalesinukrainian.FairyTale
import com.dgioto.fairytalesinukrainian.R

@Composable
fun MainScreen() {

    Scaffold(
        bottomBar = {
            BottomNavigation {

            }
            BottomNavigation {
                val selectedItemPosition = remember {
                    mutableIntStateOf(0)
                }

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )

                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        selected = selectedItemPosition.intValue == index,
                        onClick = { selectedItemPosition.intValue = index },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSecondary
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
                FairyTaleItem(fairyTale = item)
            }
        }
    }
}

@Composable
fun FairyTaleItem(fairyTale: FairyTale) {
    val isFavoriteState = remember { mutableStateOf(fairyTale.isFavorite) }

    Card(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = fairyTale.image),
                contentDescription = null,
                modifier = Modifier.size(width = 100.dp, height = 100.dp).weight(1f)
            )

            Text(
                text = fairyTale.title,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.align(alignment = Alignment.CenterVertically).weight(1f)
            )

            IconButton(
                onClick = { isFavoriteState.value = !isFavoriteState.value },
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
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