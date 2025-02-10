package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background)
                {
                    MainScreenWithBottomNavBar()
                }
            }
        }
        val i = intent
        val username = i.getStringExtra("LoginUsername")
        Toast.makeText(this, "Welcome $username to WhatsUp!!",
            Toast.LENGTH_LONG).show()
    }
}

@Composable
fun MainScreenWithBottomNavBar() {
    val navController = rememberNavController()
    var navSelectedItem by remember {
        mutableStateOf(0)
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                WhatsUpNavItemInfo().getAllNavItems().forEachIndexed { index, itemInfo ->
                    NavigationBarItem(
                        selected = (index==navSelectedItem),
                        onClick = { navSelectedItem = index
                            navController.navigate(itemInfo.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(imageVector = itemInfo.icon, contentDescription = itemInfo.label) },
                        label = { Text(text = itemInfo.label) }
                    )
                }
            }
        }
    ) { paddingValues -> NavHost( navController = navController,
            startDestination = DestinationScreens.NearMe.route,
            modifier = Modifier.padding(paddingValues)) {
            // Navigation Builder
            composable(route = DestinationScreens.Highlight.route) {
                HighlightScreen()
            }
            composable(route = DestinationScreens.NearMe.route) {
                NearMeScreen()
            }
            composable(route = DestinationScreens.MyEvents.route) {
                MyEventsScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    MyApplicationTheme {
        MainScreenWithBottomNavBar()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}