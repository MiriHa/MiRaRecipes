package de.miRa.mirarecipes.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")

    object Cookbook : Screen("controls")
    object Profile : Screen("profile")

    object DebugSettings : Screen("debugSettings")
}

internal sealed class LeafScreen(
    internal val route: String
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Health : LeafScreen("health")
}