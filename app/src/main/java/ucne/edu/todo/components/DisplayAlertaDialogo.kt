package ucne.edu.todo.components

import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ucne.edu.todo.R.*

@Composable
fun DisplayAlertaDialogo (
    nombre: String,
    message: String,
    abrirDialog: Boolean,
    cerrarDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (abrirDialog) {
        AlertDialog(
            title = {
            Text(
                text = nombre,
                fontSize = typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
        },
            text = {
                Text(
                    text = message,
                    fontSize = typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        cerrarDialog()
                    })
                {
                    Text(text = stringResource(id = string.yes))
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { cerrarDialog() })
                { Text(text = stringResource(id = string.no)) }
            },
            onDismissRequest = { cerrarDialog() })

    }
}