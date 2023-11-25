package de.miRa.mirarecipes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import de.miRa.mirarecipes.navigation.AppNavigation
import de.miRa.mirarecipes.navigation.LeafScreen
import de.miRa.mirarecipes.navigation.Screen
import de.miRa.mirarecipes.recipes.RecipesViewModel
import de.miRa.mirarecipes.ui.screens.Cookbook

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box() {
            Row(Modifier.fillMaxSize()) {
                AppNavigation(
                    navController = navController,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                Cookbook(
                    viewModel = RecipesViewModel(),
                    navigateToRecipeDetails = {
                        navController.navigate(
                            LeafScreen.CookbookOverview.RecipeDetails.createRoute(Screen.Cookbook, it),
                        )
                    },
                    navigateToRecipeEdit = {
                        navController.navigate(
                            LeafScreen.CookbookOverview.RecipeDetails.RecipeEdit.createRoute(Screen.Cookbook, it)
                        )
                    },
                    navigateToRecents = {
                        navController.navigate(
                            LeafScreen.CookbookOverview.Recent.createRoute(Screen.Cookbook)
                        )
                    }

                )
            }

            /*NotificationsScreen(
                hideNavigationBarPadding = navController.shouldShowBottomBar,
                notificationPresenting = notificationPresenting
            )*/
        }
    }
}