package jacobkingdev.teaching.todo.domain.repo

import jacobkingdev.teaching.todo.domain.model.Task
import kotlinx.datetime.Clock
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

class TaskRepository @Inject constructor() {

    private val inMemoryStore = mutableListOf<Task>(
        Task(
            description = "Run that one errand you've been putting off.",
            due = Clock.System.now().plus(1.days),
            completed = false
        ),
        Task(
            description = "Run that one errand you've been putting off.",
            due = Clock.System.now().plus(1.days),
            completed = false
        ),
        Task(
            description = "Run that one errand you've been putting off.",
            due = Clock.System.now().plus(1.days),
            completed = false
        )
    )

    fun getTasks(): List<Task> {
        return inMemoryStore
    }

    fun saveTask(task: Task) {
        deleteTask(task)
        inMemoryStore.add(task)
    }

    fun deleteTask(task: Task) {
        inMemoryStore.removeIf { it.id == task.id }
    }
}