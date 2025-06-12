package jacobkingdev.teaching.todo.ui.list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jacobkingdev.teaching.todo.domain.model.Task
import jacobkingdev.teaching.todo.domain.repo.TaskRepository
import jacobkingdev.teaching.todo.ui.theme.ToDoTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun Preview() {
    ToDoTheme {
        TaskCard(
            task = Task(
                description = "The quick brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog."
            ),
            onToggleCompleted = { },
            onEdit = { }
        )
    }
}

@Composable
fun TaskListView(
    viewModel: TaskListViewModel,
    navigateToDetailHandler: (task: Task?) -> Unit
) {

    LaunchedEffect(viewModel) {
        viewModel.fetchTasks()
    }

    Box(
        contentAlignment = Alignment.BottomEnd,
        content = {
            val tasks by viewModel.tasks.collectAsState()

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(tasks, key = { it.id }) { task ->

                        TaskCard(
                            task = task,
                            onToggleCompleted = {
                                viewModel.toggleCompleted(task.id)
                            },
                            onEdit = {
                                navigateToDetailHandler(task)
                            }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
            )
            FloatingActionButton(
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                },
                onClick = {
                    navigateToDetailHandler(null)
                },
                modifier = Modifier
                    .offset(
                        x = (-16).dp,
                        y = (-16).dp
                    )
            )
        }
    )

}

@Composable
private fun TaskCard(
    task: Task,
    onToggleCompleted: (Boolean) -> Unit,
    onEdit: () -> Unit
) {
    Card(
        content = {
            Column(
                content = {
                    Text(
                        text = task.description
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = "Due: ${task.due.toString()}",
                                modifier = Modifier
                                    .weight(1F)
                                    .fillMaxWidth()
                            )
                            Checkbox(
                                checked = task.completed,
                                onCheckedChange = onToggleCompleted,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onEdit() }
            )

        }
    )
}