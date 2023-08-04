package com.example.animationcomponent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.animationcomponent.ui.theme.AnimationComponentTheme
import com.example.animationcomponent.ui.theme.Pink80
import com.example.animationcomponent.ui.theme.Purple40
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationComponentTheme {

                var extended by remember {
                    mutableStateOf(false)
                }

                var color by remember {
                    mutableStateOf(Pink80)
                }

                val backgroundColor by animateColorAsState(color)
                val coroutineScope = rememberCoroutineScope()
                val lazyListState = rememberLazyListState()

                var expandedTopic by remember { mutableStateOf<String?>(null) }

                suspend fun showMessage() {
                    if (!extended) {
                        extended = true
                        delay(3000L)
                        extended = false
                    }
                }

                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            extended = lazyListState.isScrollInProgress,
                            onClick = {
                                coroutineScope.launch {
                                    showMessage()
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    ) {
                        AnimatedMessage(lazyListState.isScrollInProgress)

                        LazyColumn(
                            contentPadding = PaddingValues(
                                horizontal = 16.dp,
                                vertical = 32.dp
                            ),
                            state = lazyListState,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            item { Text(text = "Scroll here") }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                            item { Text(text = "Scroll here") }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                            item { Text(text = "Scroll here") }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                            item { Text(text = "Scroll here") }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                            item { Text(text = "Scroll here") }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                            item { Text(text = "Scroll here") }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                            item { Text(text = "Scroll here") }
                            item { Spacer(modifier = Modifier.height(16.dp)) }
                            item {
                                TopicRow(
                                    topic = "topic 01",
                                    expanded = expandedTopic == "topic 01",
                                    onClick = {
                                        expandedTopic =
                                            if (expandedTopic == "topic 01") null else "topic 01"
                                    }
                                )
                            }
                            item {
                                TopicRow(
                                    topic = "topic 02",
                                    expanded = expandedTopic == "topic 02",
                                    onClick = {
                                        expandedTopic =
                                            if (expandedTopic == "topic 02") null else "topic 02"
                                    }
                                )
                            }
                            item {
                                TopicRow(
                                    topic = "topic 03",
                                    expanded = expandedTopic == "topic 03",
                                    onClick = {
                                        expandedTopic =
                                            if (expandedTopic == "topic 03") null else "topic 03"
                                    }
                                )
                            }
                        }

                        Button(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 4.dp
                            ),
                            onClick = {
                                color = Pink80
                            }
                        ) {
                            Text(text = "Color 01")
                        }

                        Button(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 4.dp
                            ),
                            onClick = {
                                color = Purple40
                            }
                        ) {
                            Text(text = "Color 02")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FloatingActionButton(
    extended: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(onClick = {
        onClick
    }) {
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adding floating action button"
            )
            AnimatedVisibility(extended) {
                Text(
                    text = "Add",
                    modifier = Modifier.padding(start = 8.dp, top = 3.dp)
                )
            }
        }
    }
}

@Composable
fun AnimatedMessage(
    extended: Boolean
) {
    AnimatedVisibility(
        visible = extended,
        exit = slideOutHorizontally(
            targetOffsetX = { fullWidth: Int -> fullWidth }
        ),
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth: Int -> fullWidth }
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Add an item to the list",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun TopicRowSpacer(visible: Boolean) {
    AnimatedVisibility(visible = visible) {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopicRow(
    topic: String,
    expanded: Boolean,
    onClick: () -> Unit
) {
    TopicRowSpacer(visible = expanded)
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize()
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = topic,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    textAlign = TextAlign.Justify
                )
            }
        }
    }

    TopicRowSpacer(visible = expanded)
}


