package ucne.edu.todo.ui.screen.tarea

import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ucne.edu.todo.R.*
import ucne.edu.todo.components.DisplayAlertaDialogo
import ucne.edu.todo.data.models.Prioridad
import ucne.edu.todo.data.models.Tarea
import ucne.edu.todo.ui.theme.topAppBarBackGroundColor
import ucne.edu.todo.ui.theme.topAppBarContentColor
import ucne.edu.todo.utils.Action


@Composable
fun TareaAppBar(
    selecionarTarea: Tarea?,
    navigateToListaScreen: (Action) -> Unit
) {
    if (selecionarTarea == null)
        NuevoTaraAppBar(navigateToListaScreen = navigateToListaScreen)
    else {
        ExistingTaskAppBar(
            selecionarTarea = selecionarTarea,
            navigateToListaScreen = navigateToListaScreen
        )
    }
}

@Composable
fun NuevoTaraAppBar(
    navigateToListaScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListaScreen)
        },
        title = {
            Text(
                text = stringResource(id = string.add_task),
                color = colors.topAppBarContentColor
            )
        },
        backgroundColor = colors.topAppBarBackGroundColor,
        actions = { AddAction(onAddClicked = navigateToListaScreen) }
    )
}

@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = string.back_arrow),
            tint = colors.topAppBarContentColor
        )
    }
}

@Composable
fun AddAction(
    onAddClicked: (Action) -> Unit
) {
    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = string.add_action),
            tint = colors.topAppBarContentColor
        )
    }
}

@Composable
fun ExistingTaskAppBar(
    selecionarTarea: Tarea,
    navigateToListaScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListaScreen)
        },
        title = {
            Text(
                text = selecionarTarea.nombre,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = colors.topAppBarBackGroundColor,
        actions = {
            ExistingTareaAppBarActions(
                selecionarTarea = selecionarTarea,
                navigateToListaScreen = navigateToListaScreen,
            )
        }
    )
}

@Composable
fun ExistingTareaAppBarActions(
    selecionarTarea: Tarea,
    navigateToListaScreen: (Action) -> Unit
) {
    var abrirDialog by remember { mutableStateOf(false) }

    DisplayAlertaDialogo(
        nombre = stringResource(id = string.delete_task, selecionarTarea.nombre),
        message = stringResource(id = string.delete_task_confirmation, selecionarTarea.nombre),
        abrirDialog = abrirDialog,
        cerrarDialog =  { abrirDialog = false },
        onYesClicked = { navigateToListaScreen(Action.DELETE) }
    )

    DeleteAction(onDeleteClicked = { abrirDialog = true })
    UpdateAction(onUpdateClicked = navigateToListaScreen)
}

@Composable
fun CloseAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = string.close_icon),
            tint = colors.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteClicked: () -> Unit
) {
    IconButton(onClick = { onDeleteClicked() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = string.delete_icon),
            tint = colors.topAppBarContentColor
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = string.update_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
@Preview
fun NuevaAppBarPreview() {
    NuevoTaraAppBar(navigateToListaScreen = {})
}

@Composable
@Preview
fun ExistingAppBarPreview() {
    ExistingTaskAppBar(
        selecionarTarea = Tarea(
            id = 0,
            nombre = stringResource(id = string.title),
            descripcion = stringResource(id = string.description),
            prioridad = Prioridad.BAJO
        ),
        navigateToListaScreen = {}
    )
}