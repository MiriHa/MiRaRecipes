package de.miRa.mirarecipes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RecipeDetails(
    recipeID: String,
    openEdit: (String) -> Unit,
    navigateUp: () -> Unit,
){
    Column(modifier = Modifier.fillMaxSize().background(Color.Green)) {

    }
}