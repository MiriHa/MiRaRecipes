package de.miRa.mirarecipes.ui.screens

import androidx.compose.runtime.Composable

@Composable
fun RecipeEditCreate(
    recipeID: String? = null,
    onSave: () -> Unit,
    navigateUp: () -> Unit
){
    // If recipeID null show create
    // else prefill fields to edit
}