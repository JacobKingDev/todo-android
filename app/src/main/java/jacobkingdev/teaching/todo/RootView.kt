package jacobkingdev.teaching.todo

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.savedstate.SavedState
import jacobkingdev.teaching.todo.ui.navigation.ToDoNavDestination
import jacobkingdev.teaching.todo.ui.navigation.ToDoNavHost
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootView(

) {
    val navigationController = rememberNavController()
    val currentBackStackEntry = navigationController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    val navTitle by remember {
                        derivedStateOf {
                            currentBackStackEntry.value?.destination?.route?.let {
                                ToDoNavDestination.resolveTitle(it)
                            } ?: ""
                        }
                    }
                    Text(navTitle)
                },
                navigationIcon = {
                    val isRoot by remember {
                        derivedStateOf {
                            currentBackStackEntry.value?.destination?.route?.let {
                                navigationController.graph.startDestinationRoute == it
                            } ?: false
                        }
                    }
                    if (!isRoot) {
                        IconButton(
                            content = {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                navigationController.popBackStack()
                            }
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            ToDoNavHost(
                navController = navigationController,
                modifier = Modifier
                    .padding(paddingValues)
            )
        }
    )
}