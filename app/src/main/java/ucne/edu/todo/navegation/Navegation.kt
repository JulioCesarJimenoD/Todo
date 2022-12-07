package ucne.edu.todo.navegation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import ucne.edu.todo.navegation.destino.listaComposable
import ucne.edu.todo.navegation.destino.tareaComposable
import ucne.edu.todo.ui.viewModel.ShviewModel
import ucne.edu.todo.utils.Constante.LISTA_SCREEN

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    shviewModel: ShviewModel
) {
    val screen = remember(navController){
        Screen(navController = navController)
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = LISTA_SCREEN
    ){
//        splashComposable(
//            navigateToListScreen = screen.splash
//        )
        listaComposable(
            navigateToTareaScreen = screen.list,
            shviewModel = shviewModel
        )
        tareaComposable(
            navigateToListaScreen = screen.task,
            shviewModel = shviewModel
        )
    }

}