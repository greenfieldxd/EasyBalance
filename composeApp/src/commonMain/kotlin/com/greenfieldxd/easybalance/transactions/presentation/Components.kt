package com.greenfieldxd.easybalance.transactions.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun CustomIntTextField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    maxValue: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            val filteredValue = newValue.filter { it.isDigit() }
            val intValue = filteredValue.toIntOrNull()
            if (filteredValue.isEmpty() || (intValue != null && intValue in 0..maxValue)) {
                onValueChange(filteredValue)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None,
        modifier = modifier
            .background(color = AppColors.Surface, shape = RoundedCornerShape(8.dp))
            .border(1.dp, AppColors.Primary, shape = RoundedCornerShape(8.dp)),
        textStyle = TextStyle(color = AppColors.OnSurface, fontSize = 18.sp)
    ) { innerTextField ->
        Box(modifier = Modifier.padding(8.dp)) {
            innerTextField()
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = AppColors.LightGray,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun CustomSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = AppColors.Primary,
    backgroundColor: Color = AppColors.LightGray,
    valueRange: ClosedFloatingPointRange<Float> = 0f..10000f,
) {
    Slider(
        value = value.toFloat(),
        valueRange = valueRange.start..valueRange.endInclusive,
        onValueChange = { newValue -> onValueChange(newValue.roundToInt()) },
        steps = 0,
        modifier = modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = color,
            activeTrackColor = color,
            inactiveTrackColor = backgroundColor,
        )
    )
}

@Composable
fun CustomToggleButton(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    text: String,
    onToggle: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = text,
            color = if (isActive) AppColors.Green else AppColors.DarkGray,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = isActive,
            onCheckedChange = { onToggle(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = AppColors.Green,
                uncheckedThumbColor = AppColors.DarkGray,
                checkedTrackColor = AppColors.LightGreen,
                uncheckedTrackColor = AppColors.LightGray
            )
        )
    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppColors.Primary,
    contentColor: Color = AppColors.OnPrimary
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}