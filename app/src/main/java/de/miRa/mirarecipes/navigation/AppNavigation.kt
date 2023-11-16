package de.miRa.mirarecipes.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import de.miRa.mirarecipes.recipes.RecipesViewModel
import de.miRa.mirarecipes.ui.composables.RecipeListScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Cookbook.route,
        modifier = modifier,
        /*if (uiState.isLoggedIn) {
                    Screen.Controls.route
                } else {
                    Screen.Login.route
                },*/
        /*
                //enterTransition = { defaultEnterTransition(initialState, targetState) },
                //exitTransition = { defaultExitTransition(initialState, targetState) },
                modifier = modifier*/
    ) {
        addCookBookTopLevel()
        /* addLoginTopLevel(navController, uiState.isBenefitsCompleted)
         addDebugTopLevel(navController)
         addControlsTopLevel(navController)
         addHealthTopLevel()
         addStatisticsTopLevel()
         addNavigationTopLevel(navController)
         addProfileTopLevel(navController)*/
    }
}

@ExperimentalAnimationApi
internal fun NavGraphBuilder.addCookBookTopLevel() {
    navigation(
        route = Screen.Cookbook.route,
        startDestination = LeafScreen.Health.createRoute(Screen.Cookbook),
    ) {
        addCookbook(Screen.Cookbook)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCookbook(
    root: Screen,
) {
    composable(
        route = LeafScreen.Health.createRoute(root)
    ) {
        RecipeListScreen(viewModel = RecipesViewModel())
    }
}