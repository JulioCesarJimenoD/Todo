package ucne.edu.todo.ui.screen.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ucne.edu.todo.R.*
import ucne.edu.todo.ui.theme.fabBackgroundColor
import ucne.edu.todo.ui.viewModel.ShviewModel
import ucne.edu.todo.utils.Action
import ucne.edu.todo.utils.BuscarAppEstados

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListaScreen(
    action: Action,
    navigateToTareaScreen: (Int) -> Unit,
    shviewModel: ShviewModel
) {
    LaunchedEffect(key1 = action) {
        shviewModel.handleDatabaseActions(action = action)
    }

    val allTarea by shviewModel.allTareas.collectAsState()
    val searchedTarea by shviewModel.searchedTareas.collectAsState()
    val sortEstados by shviewModel.sortEstados.collectAsState()
    val tareasBajas by shviewModel.Prioridadbaja.collectAsState()
    val tareasAltas by shviewModel.PrioridadAlta.collectAsState()
    val buscarAppEstados : BuscarAppEstados by shviewModel.buscarAppEstados
    val buscarTextEstados: String by shviewModel.buscarTextEstados
    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        onComplete = { shviewModel.action.value = it },
        onUndoClicked = { shviewModel.action.value = it },
        tituloTarea = shviewModel.nombre.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {

            ListaAppBar(
                shviewModel = shviewModel,
                buscarAppBarEstados = buscarAppEstados,
                buscarTextEstados = buscarTextEstados
            )
        },
        content = {
            ListaContent(
                allTarea = allTarea,
                buscarTarea = searchedTarea,
                tareaBaja = tareasBajas,
                tareaAlta= tareasAltas,
                sortEstado = sortEstados,
                buscarAppEstados = buscarAppEstados,
                onSwipeToDelete = { action, tarea ->
                    shviewModel.action.value = action
                    shviewModel.updateTareaFields(selecionarTarea = tarea)
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                },
                navigateToTareaScreen = navigateToTareaScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTareaScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = { onFabClicked(1) },
        backgroundColor = colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(
                id = string.add_button_icon
            ),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onComplete: (Action) -> Unit,
    onUndoClicked: (Action) -> Unit,
    tituloTarea: String,
    action: Action
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action, tituloTarea = tituloTarea),
                    actionLabel = setActionLabel(action = action),

                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(Action.NO_ACTION)
        }
    }
}

private fun setMessage(action: Action, tituloTarea: String): String =
    when (action) {
        Action.DELETE_ALL -> "Todas las tareas eliminadas."
        else -> "${action.name}: $tituloTarea"
    }

private fun setActionLabel(action: Action): String =
    if (action.name == "BORRAR") "UNDO" else "OK"

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE
    ) onUndoClicked(Action.UNDO)
}