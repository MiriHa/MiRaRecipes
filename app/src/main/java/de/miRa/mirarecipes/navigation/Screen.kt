package de.miRa.mirarecipes.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Cookbook : Screen("cookbook")

}

internal sealed class LeafScreen(
    internal val route: String
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Login: LeafScreen("login"){
        object SignUp : LeafScreen("signUp")
        object SignIn : LeafScreen("signIn")
    }

    object CookbookOverview : LeafScreen("cookbookOverview"){
        object Recent : LeafScreen("recent")

        object RecipeCreate : LeafScreen("RecipeEdit")

        object RecipeDetails : LeafScreen("RecipeDetails/{recipeID}") {
            fun createRoute(root: Screen, recipe: String): String {
                return "${root.route}/RecipeDetails/$recipe"
            }

            object RecipeEdit : LeafScreen("RecipeEdit/{recipeID}") {
                fun createRoute(root: Screen, recipe: String): String {
                    return "${root.route}/RecipeEdit/$recipe"
                }
            }
        }
    }
}