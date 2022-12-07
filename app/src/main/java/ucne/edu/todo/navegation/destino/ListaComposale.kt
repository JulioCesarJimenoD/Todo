package ucne.edu.todo.navegation.destino

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import ucne.edu.todo.ui.screen.list.ListaScreen
import ucne.edu.todo.ui.viewModel.ShviewModel
import ucne.edu.todo.utils.Action
import ucne.edu.todo.utils.Constante.LISTA_ARGUMENTO_KEY
import ucne.edu.todo.utils.Constante.LISTA_SCREEN
import ucne.edu.todo.utils.toAction

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.listaComposable(
    navigateToTareaScreen: (tareaId: Int) -> Unit,
    shviewModel: ShviewModel
    ) {
    composable(
        route = LISTA_SCREEN,
        arguments = listOf(navArgument(LISTA_ARGUMENTO_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LISTA_ARGUMENTO_KEY).toAction()

        var myAction by rememberSaveable {(mutableStateOf(Action.NO_ACTION))}

        LaunchedEffect(key1 = myAction) {
            if (action != myAction) {
                myAction = action
                shviewModel.action.value = action
            }
        }

        val databaseAction by shviewModel.action

        ListaScreen(
            action = databaseAction,
            navigateToTareaScreen = navigateToTareaScreen,
            shviewModel = shviewModel
        )
    }
}
