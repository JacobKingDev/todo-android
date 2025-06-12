package jacobkingdev.teaching.todo.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.UUID

@Immutable
@Serializable
data class Task(
    val id: String = UUID.randomUUID().toString(),
    val description: String = "",
    val due: Instant = Clock.System.now(),
    val completed: Boolean = false
)
