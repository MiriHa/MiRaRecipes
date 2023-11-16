package de.miRa.mirarecipes.recipes

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
    // TODO make dynamic
    val isFavourite: Boolean
        get() = tags.contains(favouriteTag)

    fun toggleFavourite() {
        tags = if (isFavourite) {
            tags.minus(favouriteTag)
        } else {
            tags.plus(favouriteTag)
        }
    }
}

data class Ingredient(
    val title: String,
    val amount: String
)

const val favouriteTag = "Favourite"
