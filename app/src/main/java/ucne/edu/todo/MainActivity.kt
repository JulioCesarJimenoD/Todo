package ucne.edu.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import ucne.edu.todo.navegation.SetupNavigation
import ucne.edu.todo.ui.theme.TodoTheme
import ucne.edu.todo.ui.viewModel.ShviewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val shviewModel: ShviewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                navController = rememberAnimatedNavController()
                SetupNavigation(
                    navController = navController,
                    shviewModel = shviewModel
                )
            }
        }
    }
}
