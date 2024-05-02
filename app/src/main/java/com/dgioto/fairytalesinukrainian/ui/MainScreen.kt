package com.dgioto.fairytalesinukrainian.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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

@Composable
fun MainScreen(viewModel: MainScreenViewModel = viewModel()) {
    val navController = rememberNavController()
    val items = listOf(
        FairyTale(R.drawable.koza_dereza, "Коза дереза", "Description 1", false),
        FairyTale(R.drawable.telesik, "Телесик", "Description 2", false),
        FairyTale(R.drawable.pan_kozkiy, "Пан коцький", "Description 3", false),
        FairyTale(R.drawable.koza_dereza, "Коза дереза", "Description 1", false),
        FairyTale(R.drawable.telesik, "Телесик", "Description 2", false),
        FairyTale(R.drawable.pan_kozkiy, "Пан коцький", "Description 3", false),
        FairyTale(R.drawable.koza_dereza, "Коза дереза", "Description 1", false),
        FairyTale(R.drawable.telesik, "Телесик", "Description 2", false),
        FairyTale(R.drawable.pan_kozkiy, "Пан коцький", "Description 3", false),
        FairyTale(R.drawable.koza_dereza, "Коза дереза", "Description 1", false),
        FairyTale(R.drawable.telesik, "Телесик", "Description 2", false),
        FairyTale(R.drawable.pan_kozkiy, "Пан коцький", "Description 3", false),
    )

    Scaffold( bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavigationGraph(navController, items, viewModel, paddingValues) }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    items: List<FairyTale>,
    viewModel: MainScreenViewModel,
    paddingValues: PaddingValues
){
    NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) { HomeScreen(navController, items, viewModel, paddingValues) }
        composable(NavigationItem.Favourite.route) { FavouriteScreen(navController, viewModel, paddingValues) }
        composable(NavigationItem.Profile.route) { ProfileScreen() }
        composable(
            route = "detail/{title}",
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            val fairyTale = items.find { it.title == title }
            if (fairyTale != null) {
                FairyTaleScreen(
                    fairyTale = fairyTale,
                    onBackClick = { navController.popBackStack() },
                    viewModel = viewModel
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { Text("Ошибка: Сказка не найдена") }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
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
                label = { Text(text = stringResource(id = item.titleResId)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.surface,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun FairyTaleItem(
    navController: NavController,
    fairyTale: FairyTale,
    viewModel: MainScreenViewModel
) {
    val isFavoriteState = remember { mutableStateOf(fairyTale in viewModel.favoriteFairyTales) }

    Card(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.secondary,
            )
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
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Image(
                    painter = painterResource(id = fairyTale.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = fairyTale.title,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(start = 16.dp)
                    .align(alignment = Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.secondary
            )

            IconButton(
                onClick = {
                    if (isFavoriteState.value) viewModel.removeFromFavorites(fairyTale)
                    else viewModel.addToFavorites(fairyTale)
                    isFavoriteState.value = !isFavoriteState.value
                },
                modifier = Modifier.align(alignment = Alignment.Top)
            ) {
                Icon(
                    imageVector = if (isFavoriteState.value) Icons.Filled.Favorite
                                else Icons.Outlined.Favorite,
                    contentDescription = null,
                    tint = if (isFavoriteState.value) Color.Red else MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview
@Composable
fun LightThemePreview() {
    FairyTalesInUkrainianTheme(darkTheme = false) { MainScreen() }
}

@Preview
@Composable
fun DarkThemePreview() {
    FairyTalesInUkrainianTheme(darkTheme = true) { MainScreen() }
}