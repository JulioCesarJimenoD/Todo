package ucne.edu.todo.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ucne.edu.todo.R.*
import ucne.edu.todo.data.models.Prioridad
import ucne.edu.todo.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import ucne.edu.todo.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PrioridadDropDown(
    prioridad: Prioridad,
    onPrioridadSelecion: (Prioridad) -> Unit
){
    var expandir by remember { mutableStateOf(false) }

    val angle: Float by animateFloatAsState(
        targetValue = if (expandir) 180f else 0f
    )

    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                parentSize = it.size
            }
            .background(colors.background)
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .clickable { expandir = true }
            .border(
                width = 1.dp,
                color = colors.onSurface.copy(
                    alpha = ContentAlpha.disabled
                ),
                shape = shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1f)
        ) {
            drawCircle(color = prioridad.color)
        }
        Text(
            modifier = Modifier.weight(8f),
            text = prioridad.name,
            style = typography.subtitle2
        )
        IconButton(
            modifier = Modifier
                .alpha(alpha = ContentAlpha.medium)
                .rotate(degrees = angle)
                .weight(weight = 1.5f),
            onClick = { expandir = true }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(id = string.drop_down_arrow_icon)
            )
        }
        DropdownMenu(
            modifier = Modifier.width(with(LocalDensity.current) { parentSize.width.toDp() }),
            expanded = expandir,
            onDismissRequest = { expandir = false }
        ) {
            Prioridad.values().slice(0..2).forEach { prioridad ->
                DropdownMenuItem(
                    onClick = {
                        expandir = false
                        onPrioridadSelecion(prioridad)
                    }
                ) {
                    ItemPrioridad(prioridad = prioridad)
                }
            }
        }
    }

}

@Composable
@Preview
fun PriorityDropDownPreview() {
    PrioridadDropDown(
        prioridad = Prioridad.ALTO,
        onPrioridadSelecion = {}
    )
}