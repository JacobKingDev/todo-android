package jacobkingdev.teaching.todo.ui.list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jacobkingdev.teaching.todo.domain.model.Task
import jacobkingdev.teaching.todo.domain.repo.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {

    private val _tasks = MutableStateFlow(taskRepository.getTasks())
    val tasks: StateFlow<List<Task>>
        get() = _tasks


    fun fetchTasks() {
        _tasks.value = taskRepository.getTasks()
    }

    fun toggleCompleted(taskId: String) {
        _tasks.value = _tasks.value.map {
            if (it.id == taskId) it.copy(completed = !it.completed) else it
        }
    }
}