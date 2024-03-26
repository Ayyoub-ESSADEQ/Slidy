package com.example.slidy

import ExampleClient
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.slidy.ui.theme.MyApplicationTheme
import websocketServerSSL
import java.net.URI
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val server = websocketServerSSL(8887, resources)
        server.start()

        setContent {
            App(getIpAddress(applicationContext))
        }

        registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {}
                override fun onActivityPaused(activity: Activity) {}

                override fun onActivityStopped(activity: Activity) {
                    server.stop()
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
                override fun onActivityDestroyed(activity: Activity) {
                    server.stop()
                }
            })
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun App(ipAddress : String) {
    MyApplicationTheme {
        // A surface container using the 'background' color from the theme
        val view = LocalView.current
        val darkTheme: Boolean = isSystemInDarkTheme()
        val remoteControl = ExampleClient(
            URI("wss://localhost:8887")
        )
        remoteControl.connect()
        val presentation = Controller(remoteControl)

        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(33, 33, 33),


            ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp)
                    ) {
                        Column (
                            modifier=Modifier
                                .fillMaxWidth()
                                .background(Color(35,35,35))
                                .padding(10.dp)
                            ,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ){
                            Text(
                                text = "Here is your ip address",
                                color= Color(229,229, 229),
                            )
                            Column(
                                modifier = Modifier
                                    .wrapContentSize(align = Alignment.Center)
                                    .background(
                                        color = Color(51,51,51),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                ,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = ipAddress,
                                    color= Color.White
                                )
                            }

                            Text(
                                text = "*make sure to fill the ip field in the PowerPoint to be able to control your presentation",
                                fontSize = 1.5.em,
                                textAlign = TextAlign.Center,
                                color = Color(144, 144, 144),
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Button(
                        onClick = fun() {
                            presentation.previousSlide()
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5f) // Half the width
                    ) {
                        Text(text = "Back")
                    }

                    Button(
                        onClick = fun() {
                            presentation.nextSlide()
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth() // Half the width
                    ) {
                        Text(text = "Next")
                    }

                }
            }

        }
    }
}