package ucne.edu.todo.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import ucne.edu.todo.data.models.Prioridad
import ucne.edu.todo.ui.theme.MEDIUM_PADDING
import ucne.edu.todo.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun ItemPrioridad(prioridad: Prioridad){
    
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
            drawCircle(color = prioridad.color)
        }
        Text(
            modifier = Modifier.padding(start = MEDIUM_PADDING),
            text = prioridad.name,
            color = colors.onSurface
        )
    }
}

