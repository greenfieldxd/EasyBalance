package com.greenfieldxd.easybalance.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun CustomTextField(
    placeholder: String,
    value: String,
    borderColor: Color = AppColors.Primary,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = VisualTransformation.None,
        modifier = modifier
            .background(color = AppColors.Surface, shape = RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, shape = RoundedCornerShape(8.dp)),
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
fun CustomButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppColors.Primary,
    contentColor: Color = AppColors.OnPrimary,
    textSize: TextUnit = 16.sp
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(text = text, fontSize = textSize)
    }
}

@Composable
fun CustomSwipeBox(
    modifier: Modifier = Modifier,
    maxSwipeDistance: Dp = 200.dp,
    swipeThreshold: Float = 0.5f,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val maxSwipePx = with(LocalDensity.current) { maxSwipeDistance.toPx() }
    val offsetX = remember { Animatable(0f) }

    val dismissDirection = when {
        offsetX.value < 0 -> SwipeToDismissBoxValue.EndToStart
        offsetX.value > 0 -> SwipeToDismissBoxValue.StartToEnd
        else -> SwipeToDismissBoxValue.Settled
    }

    val (icon, alignment, color) = when (dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> Triple(Icons.Filled.Delete, Alignment.CenterEnd, AppColors.Red)
        SwipeToDismissBoxValue.StartToEnd -> Triple(Icons.Filled.Edit, Alignment.CenterStart, AppColors.Green)
        else -> Triple(Icons.Filled.Delete, Alignment.CenterEnd, Color.LightGray)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return if (abs(available.x) > abs(available.y)) {
                    Offset.Zero
                } else {
                    available
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .nestedScroll(nestedScrollConnection)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        val targetOffset = when {
                            offsetX.value <= -maxSwipePx * swipeThreshold -> -maxSwipePx * 2.5f
                            offsetX.value >= maxSwipePx * swipeThreshold -> maxSwipePx * 2.5f
                            else -> 0f
                        }

                        scope.launch {
                            offsetX.animateTo(
                                targetValue = targetOffset,
                                animationSpec = tween(300)
                            )

                            when {
                                targetOffset < 0 -> onDelete()
                                targetOffset > 0 -> onEdit()
                            }

                            offsetX.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(300)
                            )
                        }
                    }

                ) { change, dragAmount ->
                    change.consume()
                    val newOffset = offsetX.value + dragAmount
                    scope.launch { offsetX.snapTo(newOffset.coerceIn(-maxSwipePx, maxSwipePx)) }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(color, shape = MaterialTheme.shapes.medium)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .align(alignment)
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
        ) {
            content()
        }
    }
}