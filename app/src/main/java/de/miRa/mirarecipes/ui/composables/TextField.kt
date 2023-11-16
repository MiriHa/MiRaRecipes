package de.miRa.mirarecipes.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import de.miRa.mirarecipes.R
import de.miRa.mirarecipes.ui.theme.Spacings
import de.miRa.mirarecipes.ui.theme.Typography

@Composable
fun TextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean = true,
    textStyle: TextStyle = Typography.labelMedium,
    maxLines: Int = 1,
    hint: String = "Enter text here",
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        textStyle = textStyle,
        //cursorBrush = SolidColor(BentleyTheme.colors.textFieldCursor),
        /* keyboardOptions = keyboardOptions,
         keyboardActions = keyboardActions,
         interactionSource = interactionSource,*/
        maxLines = maxLines,
        singleLine = maxLines == 1,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(Spacings.l)
            )
            .padding(Spacings.s),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .background(Color.Transparent),

                contentAlignment = Alignment.CenterStart
            ) {

                innerTextField()
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_search),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(Spacings.m))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    if (value.text.isEmpty()) {
                        Text(
                            text = hint,
                            style = textStyle //TODO .withColor(if (hasError) BentleyTheme.colors.warning else BentleyTheme.colors.textFieldPlaceholderDefault),
                        )
                    }
                }
                innerTextField()
            }
        }
    )
}