package ucne.edu.todo.navegation

import androidx.navigation.NavHostController
import ucne.edu.todo.utils.Action
import ucne.edu.todo.utils.Constante.LISTA_SCREEN

import ucne.edu.todo.utils.Constante.SPLASH_SCREEN
import ucne.edu.todo.utils.Constante.TAREA_SCREEN

class Screen (navController: NavHostController) {

//    val splah: () -> Unit ={
//        navController.navigate(route = "lista/${Action.NO_ACTION}"){
//            popUpTo(TAREA_SCREEN){ inclusive = true}
//        }
//    }


    val list: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }

    val task: (Action) -> Unit = {action ->
        navController.navigate(route = "list/${action.name}"){
            popUpTo(LISTA_SCREEN) { inclusive = true}
        }
    }

}