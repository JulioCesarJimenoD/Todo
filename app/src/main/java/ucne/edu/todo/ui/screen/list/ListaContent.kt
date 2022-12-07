package ucne.edu.todo.ui.screen.list

import android.annotation.SuppressLint
import android.text.TextUtils.isEmpty
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ucne.edu.todo.R.*
import ucne.edu.todo.data.models.Prioridad
import ucne.edu.todo.data.models.Tarea
import ucne.edu.todo.navegation.destino.EmptyContent
import ucne.edu.todo.ui.theme.*
import ucne.edu.todo.utils.Action
import ucne.edu.todo.utils.BuscarAppEstados
import ucne.edu.todo.utils.Estados



@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListaContent(
    allTarea: Estados<List<Tarea>>,
    buscarTarea: Estados<List<Tarea>>,
    tareaBaja: List<Tarea>,
    tareaAlta: List<Tarea>,
    sortEstado: Estados<Prioridad>,
    buscarAppEstados: BuscarAppEstados,
    onSwipeToDelete: (Action, Tarea) -> Unit,
    navigateToTareaScreen: (tareaId: Int) -> Unit
) {
    if (sortEstado is Estados.Exito) {
        when {
            buscarAppEstados == BuscarAppEstados.TRIGGERED -> {
                if (buscarTarea is Estados.Exito) {
                    HandleListContent(
                        tareas = buscarTarea.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTareaScreen = navigateToTareaScreen
                    )
                }
            }
            sortEstado.data == Prioridad.NONE -> {
                if (allTarea is Estados.Exito) {
                    HandleListContent(
                        tareas = allTarea.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTareaScreen = navigateToTareaScreen
                    )
                }
            }
            sortEstado.data == Prioridad.BAJO -> {
                HandleListContent(
                    tareas = tareaBaja,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTareaScreen = navigateToTareaScreen
                )
            }
            sortEstado.data == Prioridad.ALTO -> {
                HandleListContent(
                    tareas = tareaAlta,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTareaScreen = navigateToTareaScreen
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    tareas: List<Tarea>,
    onSwipeToDelete: (Action, Tarea) -> Unit,
    navigateToTareaScreen: (taskId: Int) -> Unit
) {
    if (tareas.isEmpty())
        EmptyContent()
    else {
        DisplayTasks(
            tareas = tareas,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTareaScreen = navigateToTareaScreen
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    tareas: List<Tarea>,
    onSwipeToDelete: (Action, Tarea) -> Unit,
    navigateToTareaScreen: (tareaId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = tareas,
            key = { tarea -> tarea.id }
        )
        { tarea ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeToDelete(Action.DELETE,tarea)
                }
            }

            val degrees by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0f else -45f
            )

            var itemAppeared by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = ANIMATION_DURATION
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = ANIMATION_DURATION
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                    background = { RedBackground(degrees = degrees) },
                    dismissContent = {
                        TareaItem(
                            tarea = tarea,
                            navigateToTaskScreen = navigateToTareaScreen
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGE_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = string.delete_icon),
            tint = Color.White
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun TareaItem(
    tarea: Tarea,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TAREA_ITEM_ELEVATION,
        onClick = { navigateToTaskScreen(tarea.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(all = MEDIUM_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = tarea.nombre,
                    color = colors.taskItemTextColor,
                    style = typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .width(PRIORITY_INDICATOR_SIZE)
                            .height(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = tarea.prioridad.color
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = tarea.descripcion,
                color = colors.taskItemTextColor,
                style = typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun TaskItemPreview() {
    TareaItem(
        tarea = Tarea(
            0,
            stringResource(id = string.title),
            stringResource(id = string.description),
            Prioridad.MEDIO
        ),
        navigateToTaskScreen = {}
    )
}

@Composable
@Preview
fun RedBackgroundPreview() {
    Column(modifier = Modifier.height(80.dp)) {
        RedBackground(degrees = 0f)
    }
}