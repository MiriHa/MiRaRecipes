package de.miRa.mirarecipes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.miRa.mirarecipes.R


object MiraRecipesFonts {
    val Regular: FontFamily = FontFamily(
        Font(R.font.ubuntu_light, FontWeight.Light),
        Font(R.font.ubuntu_regular, FontWeight.Normal),
        Font(R.font.ubuntu_medium, FontWeight.Medium),
        Font(R.font.ubuntu_bold, FontWeight.Bold),
    )
}

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = MiraRecipesFonts.Regular,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MiraRecipesFonts.Regular,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = MiraRecipesFonts.Regular,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = MiraRecipesFonts.Regular,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 16.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = MiraRecipesFonts.Regular,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = MiraRecipesFonts.Regular,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = MiraRecipesFonts.Regular,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 12.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = MiraRecipesFonts.Regular,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 10.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = MiraRecipesFonts.Regular,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 10.sp,
    ),

)