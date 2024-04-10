package com.dgioto.fairytalesinukrainian.ui

import android.annotation.SuppressLint
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dgioto.fairytalesinukrainian.R
import com.dgioto.fairytalesinukrainian.models.FairyTale
import com.dgioto.fairytalesinukrainian.ui.theme.FairyTalesInUkrainianTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        FairyTale(R.drawable.koza_dereza, "Коза дереза", "Description 1", false),
        FairyTale(R.drawable.telesik, "Телесик", "Description 2", false),
        FairyTale(R.drawable.pan_kozkiy, "Пан коцький", "Description 3", false),
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
            composable(NavigationItem.Home.route) { HomeScreen(navController, items) }
            composable(NavigationItem.Favourite.route) { FavouriteScreen() }
            composable(NavigationItem.Profile.route) { ProfileScreen() }
            composable(
                route = "detail/{title}",
                arguments = listOf(navArgument("title") { type = NavType.StringType })
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title")
                val fairyTale = items.find { it.title == title }
                if (fairyTale != null) {
                    DetailScreen(
                        fairyTale = fairyTale,
                        onBackClick = { navController.popBackStack() })
                } else Text("Ошибка: Сказка не найдена")
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        var navigationSelectedItem by remember { mutableStateOf(0) }
        val navItems = listOf(NavigationItem.Home, NavigationItem.Favourite, NavigationItem.Profile)

        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                onClick = {
                    navigationSelectedItem = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if (index == navigationSelectedItem) item.selectedIcon
                        else item.unselectedIcon,
                        contentDescription = item.titleResId.toString()
                    )
                },
                label = { Text(text = stringResource(id = item.titleResId)) }
            )
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController, items: List<FairyTale>) {
    LazyColumn(modifier = Modifier.padding()) {
        items(items) { item ->
            FairyTaleItem(navController, item)
        }
    }
}

@Composable
fun FavouriteScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { Text("Favourite Screen") }
}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { Text("Profile  Screen") }
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
fun FairyTaleItem(navController: NavController, fairyTale: FairyTale) {
    val isFavoriteState = remember { mutableStateOf(fairyTale.isFavorite) }

    Card(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
    ) {
        // Обработчик нажатия добавлен к Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // Переход на DetailScreen при нажатии
                    navController.navigate("detail/${fairyTale.title}")
                },
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