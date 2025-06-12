package jacobkingdev.teaching.todo.ui.detail

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import jacobkingdev.teaching.todo.domain.model.Task
import jacobkingdev.teaching.todo.domain.repo.TaskRepository

@HiltViewModel(assistedFactory = TaskDetailViewModel.Factory::class)
class TaskDetailViewModel @AssistedInject constructor(
    private val taskRepository: TaskRepository,
    @Assisted private val task: Task?
): ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(task: Task?): TaskDetailViewModel
    }
}