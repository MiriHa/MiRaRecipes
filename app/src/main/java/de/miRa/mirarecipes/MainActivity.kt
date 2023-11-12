package de.miRa.mirarecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import de.miRa.mirarecipes.recipes.RecipesViewModel
import de.miRa.mirarecipes.ui.composables.RecipeListScreen
import de.miRa.mirarecipes.ui.theme.MiRaRecipesTheme

class MainActivity : ComponentActivity() {

    private val recipesViewModel = RecipesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiRaRecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeListScreen(recipesViewModel, /*navController*/)

                }
            }
        }
    }
}
