package de.miRa.mirarecipes.recipes

import androidx.compose.runtime.mutableStateOf

data class Recipe(
    val id: Int? = null,
    val title: String = "",
    val featuredImage: String? = null,
    var tags: List<String> = listOf(),
    val sourceUrl: String? = null,
    val description: String? = null,
    val cookingInstructions: List<String>? = null,
    val ingredients: List<Ingredient> = listOf(),
    val dateAdded: String? = null,
    val dateUpdated: String? = null,
) {
    var isFavourite = mutableStateOf(tags.contains(favouriteTag))

    fun updateFavouriteState() {
        tags = if (tags.contains(favouriteTag)) {
            isFavourite.value = false
            tags.minus(favouriteTag)
        } else {
            isFavourite.value = true
            tags.plus(favouriteTag)
        }
    }
}

data class Ingredient(
    val title: String,
    val amount: String
)

const val favouriteTag = "Favourite"
