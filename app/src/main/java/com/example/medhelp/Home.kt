package com.example.medhelp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainMenu()
        }
    }
    @Preview
    @Composable
    fun MainMenu() {
        var isExitPressed by remember { mutableStateOf(false) }
        var isListPressed by remember { mutableStateOf(false) }
        var isProfilePressed by remember { mutableStateOf(false) }
        val font1 = FontFamily(
            Font(R.font.font1)
        )
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFF3F0F0), Color(0xFFFAF7F7)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(
                                1f,
                                2f
                            ),
                            tileMode = TileMode.Clamp
                        )
                    )
            )
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "MedHelp",
                    color = Color.Blue,
                    fontSize = 40.sp,
                    fontFamily = font1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        tint = if (isProfilePressed) Color.Gray else Color.Blue,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(48.dp)
                            .clickable {
                                isProfilePressed = true
                                val intent = Intent(this@Home, Profile::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .alpha(if (isProfilePressed) 0.5f else 1f)
                            .animateContentSize()
                    )
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = "Patients",
                        tint = if (isListPressed) Color.Gray else Color.Blue,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(48.dp)
                            .clickable {
                                isListPressed = true
                                val intent = Intent(this@Home, Pacienty::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .alpha(if (isListPressed) 0.5f else 1f)
                            .animateContentSize()
                    )
                    Icon(
                       imageVector = Icons.Filled.ExitToApp,
                       contentDescription = "Home",
                       tint = if (isExitPressed) Color.Gray else Color.Blue,
                       modifier = Modifier
                           .padding(16.dp)
                           .size(48.dp)
                           .clickable {
                               isExitPressed = true
                               val intent = Intent(this@Home, MainActivity::class.java)
                               startActivity(intent)
                               finish()
                           }
                           .alpha(if (isExitPressed) 0.5f else 1f)
                           .animateContentSize()
                   )
                }
            }
        }
    }
}