package jacobkingdev.teaching.todo

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class Task(
    val id: String = UUID.randomUUID().toString(),
    val description: String = ""
)
