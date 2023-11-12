package de.miRa.mirarecipes.recipes

data class Recipe (
    val id: Int? = null,
    val title: String = "",
    val featuredImage: String? = null,
    val tags: List<FilterTag> = listOf(),
    val sourceUrl: String? = null,
    val description: String? = null,
    val cookingInstructions: List<String>? = null,
    val ingredients: List<Ingredient> = listOf(),
    val dateAdded: String? = null,
    val dateUpdated: String? = null,
)

data class Ingredient(
    val title: String,
    val amount: String
)
