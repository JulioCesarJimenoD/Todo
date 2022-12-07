package ucne.edu.todo.ui.screen.tarea

import android.icu.text.DateFormat.MEDIUM
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ucne.edu.todo.R.*
import ucne.edu.todo.components.PrioridadDropDown
import ucne.edu.todo.data.models.Prioridad
import ucne.edu.todo.ui.theme.MEDIUM_PADDING
import ucne.edu.todo.ui.theme.SMALL_PADDING


@Composable
fun TareaContent(
    nombre: String,
    onTitleChange: (String) -> Unit,
    descripcion: String,
    onDescriptionChange: (String) -> Unit,
    prioridad: Prioridad,
    onPrioridadSelecion: (Prioridad) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(all = MEDIUM_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = nombre,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(id = string.title)) },
            textStyle = typography.body1,
            singleLine = true
        )
        Divider(
            modifier = Modifier.height(SMALL_PADDING),
            color = colors.background
        )

        PrioridadDropDown(
            prioridad = prioridad,
            onPrioridadSelecion = onPrioridadSelecion
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = descripcion,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(id = string.description)) },
            textStyle = typography.body1
        )
    }
}

@Composable
//@Preview
private fun TaskContentPreview() {
    TareaContent(
        nombre = "",
        onTitleChange = {},
        descripcion = "",
        onDescriptionChange = {},
        prioridad = Prioridad.MEDIO,
        onPrioridadSelecion = {}
    )
}