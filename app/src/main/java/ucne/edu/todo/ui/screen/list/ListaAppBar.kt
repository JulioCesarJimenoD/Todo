package ucne.edu.todo.ui.screen.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ucne.edu.todo.R.*
import ucne.edu.todo.components.DisplayAlertaDialogo
import ucne.edu.todo.components.ItemPrioridad
import ucne.edu.todo.data.models.Prioridad
import ucne.edu.todo.ui.theme.topAppBarBackGroundColor
import ucne.edu.todo.ui.theme.topAppBarContentColor
import ucne.edu.todo.ui.viewModel.ShviewModel
import ucne.edu.todo.utils.Action
import ucne.edu.todo.utils.BuscarAppEstados
import kotlin.text.Typography




@Composable
fun ListaAppBar(
    shviewModel: ShviewModel,
    buscarAppBarEstados: BuscarAppEstados,
    buscarTextEstados: String
) {
    when (buscarAppBarEstados) {
        BuscarAppEstados.CLOSED -> {
            DefaultListaAppBar(
                onSearchClicked = {
                    shviewModel.buscarAppEstados.value =
                        BuscarAppEstados.OPENED
                },
                onSortClicked = { shviewModel.persistSortEstado(it) },
                onDeleteAllConfirmed = {
                    shviewModel.action.value = Action.DELETE_ALL
                }
            )
        }
        else -> {
            SearchAppBar(
                text = buscarTextEstados,
                onTextChange = { nuevoText ->
                    shviewModel.buscarTextEstados.value = nuevoText
                },
                onCloseClicked = {
                    shviewModel.buscarAppEstados.value =
                        BuscarAppEstados.CLOSED
                    shviewModel.buscarTextEstados.value = ""
                },
                onSearchClicked = {
                    shviewModel.buscarBaseDatos(buscarQuery =it)
                }
            )
        }
    }
}

@Composable
fun DefaultListaAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Prioridad) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {

    TopAppBar(
        title = {
        Text(  text = stringResource(id = string.tarea),
        color = Color.White)
    },
        actions = {
            ListaAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllConfirmed = onDeleteAllConfirmed
            )
        }
    )
}



@Composable
fun ListaAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Prioridad) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    var abrirDialog by remember { mutableStateOf(false) }

    DisplayAlertaDialogo(
        nombre = stringResource(id = string.delete_all_tasks),
        message = stringResource(id = string.delete_all_tasks_confirmation),
        abrirDialog = abrirDialog,
        cerrarDialog = { abrirDialog = false },
        onYesClicked = { onDeleteAllConfirmed() }
    )

    BuscarAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteAllConfirmed = { abrirDialog = true })
}

@Composable
fun BuscarAction(
    onSearchClicked: () -> Unit
) {
    IconButton(
        onClick = { onSearchClicked() }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Buscar Tarea",
            tint = Color.LightGray
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Prioridad) -> Unit
) {
    var expandir by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expandir = true }
    ) {
        Icon(
            painter = painterResource(id = drawable.filtro),
            contentDescription = "Ordenar tareas",
            tint = Color.White
        )
        DropdownMenu(
            expanded =  expandir,
            onDismissRequest = { expandir = false }
        ) {
            Prioridad.values().slice(setOf(0, 2, 3)).forEach { prioridad ->
                DropdownMenuItem(
                    onClick = {
                        expandir = false
                        onSortClicked(prioridad)
                    }
                ) {
                    ItemPrioridad( prioridad = prioridad)
                }
            }
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteAllConfirmed: () -> Unit
) {
    var expandir by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expandir = true }
    ) {
        Icon(
            painter = painterResource(id = drawable.ic_more),
            contentDescription = "Borrar todo",
            tint = colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expandir,
            onDismissRequest = { expandir = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expandir = false
                    onDeleteAllConfirmed()
                }
            ) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = "Borrar todo"
                )
            }
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = colors.topAppBarBackGroundColor
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = stringResource(id = string.search),
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = colors.topAppBarContentColor,
                fontSize = typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.disabled),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = string.delete_all_action),
                        tint = colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty())
                            onTextChange("")
                        else
                            onCloseClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = string.close_icon),
                        tint = colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Composable
@Preview
private fun DefaultListaAppBarPreview() {
    DefaultListaAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteAllConfirmed = {}
    )
}

@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}
