package com.dgioto.fairytalesinukrainian.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dgioto.fairytalesinukrainian.models.FairyTale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FairyTaleScreen(
    fairyTale: FairyTale,
    onBackClick: () -> Unit,
    viewModel: MainScreenViewModel
) {
    val isFavoriteState = remember { mutableStateOf(fairyTale in viewModel.favoriteFairyTales) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column {
            TopAppBar(
                title = { Text(text = fairyTale.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isFavoriteState.value) viewModel.removeFromFavorites(fairyTale)
                        else viewModel.addToFavorites(fairyTale)
                        isFavoriteState.value = !isFavoriteState.value
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite, // Adjust based on favorite state
                            contentDescription = null,
                            tint = if (isFavoriteState.value) Color.Red
                            else MaterialTheme.colorScheme.onPrimary // Adjust based on favorite state
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
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
                modifier = Modifier.padding(all = 16.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}