package jacobkingdev.teaching.todo.ui.navigation

import jacobkingdev.teaching.todo.domain.model.Task
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
object ToDoNavDestination {

    @Serializable
    data object List

    @Serializable
    data class Details(val task: Task)

    fun resolveTitle(rawRoute: String): String {

        // In compose type-safe navigation, serialised routes use are comprised of the route, a '/'
        // delimiter, then the arguments. If we strip the characters after '/', we can use the
        // resulting string to lookup the title for that screen.

        val trimmedRoute = rawRoute.substringBefore("/")
        return when (trimmedRoute) {
            List::class.qualifiedName -> "Your Tasks"
            Details::class.qualifiedName -> "Task Details"
            else -> ""
        }
    }
}
