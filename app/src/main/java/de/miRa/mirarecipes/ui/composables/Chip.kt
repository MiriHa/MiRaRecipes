package de.miRa.mirarecipes.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import de.miRa.mirarecipes.ui.theme.Spacings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagChip(
    label: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    FilterChip(
        modifier = modifier.padding(end = Spacings.xxs),
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        trailingIcon = trailingIcon,
        interactionSource = interactionSource,
        label = { Text(label) },
        leadingIcon = leadingIcon ?: {
            /* TODO if (selected) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }*/
        },
        shape = RoundedCornerShape(Spacings.l),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.surface,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            iconColor = MaterialTheme.colorScheme.onSurface,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTrailingIconColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = Color.Transparent,
            selectedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        )
    )
}

@Composable
fun IconChip(
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Close,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    AssistChip(
        modifier = modifier.padding(end = Spacings.xxs),
        onClick = onClick,
        label = {
            Icon(icon, contentDescription = null)
        },
        enabled = enabled,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(Spacings.l),
        colors = AssistChipDefaults.assistChipColors(
            labelColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = AssistChipDefaults.assistChipBorder(
            borderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent
        )
    )
}

@Composable
fun StaticChip(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(Spacings.l))
            .padding(horizontal = Spacings.xs, vertical = Spacings.xxxs),
        text = title,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.labelMedium
    )
}