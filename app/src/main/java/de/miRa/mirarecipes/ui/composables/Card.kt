package de.miRa.mirarecipes.ui.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import de.miRa.mirarecipes.ui.theme.Elevations
import de.miRa.mirarecipes.ui.theme.Spacings

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = Spacings.l,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier/*.then(
            Modifier.shadow(
                ambientColor = cardShadowLayerSecond,
                spotColor = cardShadowLayerSecond,
                shape = RoundedCornerShape(Spacings.l),
                elevation = Elevations.card
                )
        )*/,
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Elevations.card
        ),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        content()
    }
}

/*
@Composable
fun CardNoElevation(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Spacings.xs),
    backgroundColor: Color = SemanticColors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = 0.dp
    ) {
        content()
    }
}
*/
