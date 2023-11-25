package de.miRa.mirarecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import de.miRa.mirarecipes.recipes.RecipesViewModel
import de.miRa.mirarecipes.ui.theme.MiRaRecipesTheme

class MainActivity : ComponentActivity() {

    private val recipesViewModel = RecipesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiRaRecipesTheme {
                MainScreen()
            }
        }
    }
}
