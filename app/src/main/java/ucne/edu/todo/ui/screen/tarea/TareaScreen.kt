package ucne.edu.todo.ui.screen.tarea

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import ucne.edu.todo.data.models.Prioridad
import ucne.edu.todo.data.models.Tarea
import ucne.edu.todo.ui.viewModel.ShviewModel
import ucne.edu.todo.utils.Action


@Composable
fun TareaScreen(
    selectedTarea: Tarea?,
    shviewModel: ShviewModel,
    navigateToListaScreen: (Action) -> Unit
){

    val nombre: String by shviewModel.nombre
    val descripcion: String by shviewModel.descripcion
    val prioridad: Prioridad by shviewModel.prioridad

    val context = LocalContext.current

    BackHandler { navigateToListaScreen(Action.NO_ACTION) }

    androidx.compose.material.Scaffold(
        topBar = {
            TareaAppBar(
                selecionarTarea = selectedTarea,
                navigateToListaScreen = { action ->
                    if (action == Action.NO_ACTION)
                        navigateToListaScreen(action)
                    else {
                        if (shviewModel.validateFields())
                            navigateToListaScreen(action)
                        else toast(context = context)
                    }
                }
            )
        },
        content = {
            TareaContent(
                nombre = nombre,
                onTitleChange = {
                    shviewModel.updateNombre(it)
                },
                descripcion = descripcion,
                onDescriptionChange = {
                    shviewModel.descripcion.value = it
                },
                prioridad = prioridad,
                onPrioridadSelecion= {
                    shviewModel.prioridad.value = it
                }
            )
        }
    )
}

fun toast(context: Context) {
    Toast.makeText(context, "Campos Vacío.", Toast.LENGTH_SHORT).show()
}