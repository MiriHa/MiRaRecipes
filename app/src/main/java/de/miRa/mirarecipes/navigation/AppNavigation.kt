package de.miRa.mirarecipes.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import de.miRa.mirarecipes.ui.screens.Recent
import de.miRa.mirarecipes.ui.screens.RecipeDetails
import de.miRa.mirarecipes.ui.screens.RecipeEditCreate
import de.miRa.mirarecipes.recipes.RecipesViewModel
import de.miRa.mirarecipes.ui.screens.Cookbook


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
        addCookBookTopLevel(navController)
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
internal fun NavGraphBuilder.addCookBookTopLevel(
    navController: NavHostController
) {
    navigation(
        route = Screen.Cookbook.route,
        startDestination = LeafScreen.CookbookOverview.createRoute(Screen.Cookbook),
    ) {
        addCookbook(navController, Screen.Cookbook)
        addRecents(navController, Screen.Cookbook)
        addRecipeEdit(navController, Screen.Cookbook)
        addRecipeDetails(navController, Screen.Cookbook)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCookbook(
    navController: NavHostController,
    root: Screen,
) {
    composable(
        route = LeafScreen.CookbookOverview.createRoute(root),
    ) {
        Cookbook(
            viewModel = RecipesViewModel(),
            navigateToRecipeDetails = {
                navController.navigate(
                    LeafScreen.CookbookOverview.RecipeDetails.createRoute(root, it),
                )
            },
            navigateToRecipeEdit = {
                navController.navigate(
                    LeafScreen.CookbookOverview.RecipeDetails.RecipeEdit.createRoute(root, it)
                )
            },
            navigateToRecents = {
                navController.navigate(
                    LeafScreen.CookbookOverview.Recent.createRoute(root)
                )
            }

        )
    }
}

@ExperimentalAnimationApi
internal fun NavGraphBuilder.addRecents(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.CookbookOverview.Recent.createRoute(root)
    ) {
   /* TODO     val backStackEntry = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(
                LeafScreen.CookbookOverview.createRoute(
                    root
                )
            )
        }*/

        //TODO val viewModel: VehicleDetailViewModel = hiltViewModel(backStackEntry)
        val viewModel = RecipesViewModel()
        Recent(viewModel = viewModel, navigateUp = navController::navigateUp)
    }
}

@ExperimentalAnimationApi
internal fun NavGraphBuilder.addRecipeDetails(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.CookbookOverview.RecipeDetails.createRoute(root),
        arguments = listOf(
            navArgument("recipeID") { type = NavType.StringType }
        )
    ) {backStackEntry ->
        backStackEntry.arguments?.getString("recipeID")?.let { recipe ->
            RecipeDetails(
                recipeID = recipe,
                openEdit = {
                    navController.navigate(
                        LeafScreen.CookbookOverview.RecipeDetails.RecipeEdit.createRoute(root, it)
                    )
                },
                navigateUp = navController::navigateUp
            )
        }
    }
}

@ExperimentalAnimationApi
internal fun NavGraphBuilder.addRecipeEdit(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.CookbookOverview.RecipeDetails.RecipeEdit.createRoute(root),
        arguments = listOf(navArgument("recipeID") { type = NavType.StringType })
    ) {
        RecipeEditCreate(
            recipeID = it.arguments?.getString("recipeID"),
            onSave = {

            },
            navigateUp = navController::navigateUp
        )
    }
}
