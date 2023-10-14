package com.nezihyilmaz.composeshaderplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nezihyilmaz.composeshaderplayground.ui.Coloring
import com.nezihyilmaz.composeshaderplayground.ui.Highlight
import com.nezihyilmaz.composeshaderplayground.ui.Pixelate
import com.nezihyilmaz.composeshaderplayground.ui.theme.ComposeShaderPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeShaderPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { Home {
                            when(it){
                                0 -> navController.navigate("coloring")
                                1 -> navController.navigate("highlight")
                                2 -> navController.navigate("pixelate")
                            }
                        } }
                        composable("coloring") { Coloring() }
                        composable("highlight") { Highlight() }
                        composable("pixelate") { Pixelate() }
                    }
                }
            }
        }
    }
}

@Composable
fun Home(onItemSelected: (Int) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = {onItemSelected(0)}) {
                Text(text = "Coloring")
            }

            Button(onClick = {onItemSelected(1)}) {
                Text(text = "Highlight")
            }

            Button(onClick = {onItemSelected(2)}) {
                Text(text = "Pixelate")
            }
        }
    }
}

