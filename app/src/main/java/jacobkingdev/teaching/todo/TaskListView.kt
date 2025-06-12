package jacobkingdev.teaching.todo

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jacobkingdev.teaching.todo.theme.ToDoTheme

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun Preview() {
    ToDoTheme {
        TaskCard(
            task = Task(description = "The quick brown fox jumps over the lazy dog."),
            onTextChanged = { },
            onRemove = { }
        )
    }
}

@Composable
fun TaskListView(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        content = {
            var tasks by remember { mutableStateOf(listOf<Task>()) }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(tasks, key = { it.id }) { task ->
                        TaskCard(
                            task = task,
                            onTextChanged = { newValue ->
                                tasks = tasks.map {
                                    if (it.id == task.id) it.copy(description = newValue) else it
                                }
                            },
                            onRemove = {
                                tasks = tasks.mapNotNull {
                                    if (it.id == task.id) null else it
                                }
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
                    tasks = tasks.toMutableList().apply {
                        add(Task())
                    }
                },
                modifier = Modifier
                    .offset(
                        x = (-16).dp,
                        y = (-16).dp
                    )
            )
        },
        modifier = modifier
    )

}

@Composable
private fun TaskCard(
    task: Task,
    onTextChanged: (String) -> Unit,
    onRemove: () -> Unit
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { change ->
            change == SwipeToDismissBoxValue.EndToStart
        }
    )
    if (swipeToDismissBoxState.currentValue == SwipeToDismissBoxValue.EndToStart && swipeToDismissBoxState.progress == 1F) {
        onRemove()
    }
    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        backgroundContent = {
            if (swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    content = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CardDefaults.shape)
                        .background(Color.Red)
                )
            }
        },
        content = {
            Card(
                content = {
                    Column(
                        content = {
                            val focusRequester = remember { FocusRequester() }
                            LaunchedEffect(Unit) {
                                focusRequester.requestFocus()
                            }
                            TextField(
                                value = task.description,
                                onValueChange = onTextChanged,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester)
                            )
                        },
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            )
        }
    )

}