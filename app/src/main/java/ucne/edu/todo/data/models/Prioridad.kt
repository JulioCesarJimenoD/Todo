package ucne.edu.todo.data.models

import ucne.edu.todo.ui.theme.HighColor
import ucne.edu.todo.ui.theme.LowColor
import ucne.edu.todo.ui.theme.MediumColor
import ucne.edu.todo.ui.theme.NoneColor
import androidx.compose.ui.graphics.Color

enum class Prioridad(val color:Color) {
    ALTO(HighColor),
    MEDIO(MediumColor),
    BAJO(LowColor),
    NONE(NoneColor)
}