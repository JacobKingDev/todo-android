package jacobkingdev.teaching.todo.ui.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

@Composable
fun ToDoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ToDoNavDestination.List,
        route = ToDoNavDestination::class,
        builder = {
            composable<ToDoNavDestination.List>{
                Button(content = {
                    Text("Nav")
                }, onClick = {
                    navController.navigate(ToDoNavDestination.Details(9))
                })
            }
            composable<ToDoNavDestination.Details> { navBackStackEntry ->
                val todoItemId = navBackStackEntry.toRoute<ToDoNavDestination.Details>().todoItemId
                Text(todoItemId.toString())
            }

        },
        modifier = modifier
    )
}