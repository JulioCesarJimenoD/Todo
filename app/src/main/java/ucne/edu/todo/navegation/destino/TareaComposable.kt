package ucne.edu.todo.navegation.destino

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import ucne.edu.todo.ui.screen.tarea.TareaScreen
import ucne.edu.todo.ui.viewModel.ShviewModel
import ucne.edu.todo.utils.Action
import ucne.edu.todo.utils.Constante
import ucne.edu.todo.utils.Constante.TAREA_ARGUMENT_KEY
import ucne.edu.todo.utils.Constante.TAREA_SCREEN


@ExperimentalAnimationApi
fun NavGraphBuilder.tareaComposable(
    shviewModel: ShviewModel,
    navigateToListaScreen: (Action) -> Unit
) {
    composable(
        route = TAREA_SCREEN,
        arguments = listOf(navArgument(TAREA_ARGUMENT_KEY) {
            type = NavType.IntType
        }),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(300)
            )
        }
    ) {
            navBackStackEntry ->
        val tareaId = navBackStackEntry.arguments!!.getInt(TAREA_ARGUMENT_KEY)

        LaunchedEffect(key1 = tareaId) {
            shviewModel.getSelecionarTarea(tareaId = tareaId)
        }
        val selecionTarea by shviewModel.selectedTarea.collectAsState()

        LaunchedEffect(key1 = selecionTarea) {
            if (selecionTarea != null || tareaId == -1)
                shviewModel.updateTareaFields(selecionarTarea = selecionTarea)
        }

        TareaScreen(
            selectedTarea = selecionTarea,
             shviewModel= shviewModel,
            navigateToListaScreen = navigateToListaScreen
        )
    }
}