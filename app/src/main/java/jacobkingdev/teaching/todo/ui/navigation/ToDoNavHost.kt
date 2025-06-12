package jacobkingdev.teaching.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import jacobkingdev.teaching.todo.domain.model.Task
import jacobkingdev.teaching.todo.ui.detail.TaskDetailView
import jacobkingdev.teaching.todo.ui.detail.TaskDetailViewModel
import jacobkingdev.teaching.todo.ui.list.TaskListView
import kotlin.reflect.typeOf

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
            composable<ToDoNavDestination.List>(
                typeMap =  mapOf (
                    typeOf<Task>() to JsonRepresentableNavType(Task.serializer()),
                )
            ) {
                TaskListView(
                    viewModel = hiltViewModel(),
                    navigateToDetailHandler = { task ->
                        navController.navigate(ToDoNavDestination.Details(task ?: Task()))
                    }
                )
            }
            composable<ToDoNavDestination.Details>(
                typeMap =  mapOf (
                    typeOf<Task>() to JsonRepresentableNavType(Task.serializer()),
                )
            ) { navBackStackEntry ->
                val route = navBackStackEntry.toRoute<ToDoNavDestination.Details>()
                TaskDetailView(
                    viewModel = hiltViewModel(
                        creationCallback = { factory: TaskDetailViewModel.Factory ->
                            factory.create(
                                task = route.task
                            )
                        }
                    )
                )
            }

        },
        modifier = modifier
    )
}