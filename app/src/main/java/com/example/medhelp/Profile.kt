package com.example.medhelp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class Profile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopAppBar {
                IconButton(onClick = {
                    val intent = Intent(this@Profile, Home::class.java)
                    startActivity(intent)
                    finish()
                }) {
                    Text("Назад")
                }
            }
            ProfileScreen()
        }
    }
    @Preview
    @Composable
    fun ProfileScreen() {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(color = Color.LightGray)
                        .padding(16.dp)
                ) { }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = Singleton.fio,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            val db = Firebase.firestore
                            val prov = db.collection("MedSestry").document(Singleton.fio)
                            prov.get()
                                .addOnSuccessListener { document ->
                                    if (document != null) {
                                        val user = Users(
                                            fio = Singleton.fio,
                                            password = password
                                        )
                                        db.collection("MedSestry").document(Singleton.fio)
                                            .set(user)
                                            .addOnSuccessListener {
                                                val intent = Intent(this@Profile, Home::class.java)
                                                startActivity(intent)
                                                finish()
                                            }
                                            .addOnFailureListener {
                                            }
                                    }
                                }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(top = 8.dp)
                    ) {
                        Text(text = "Изменить пароль")
                    }

                    Button(
                        onClick = {
                            val intent = Intent(this@Profile, Home::class.java)
                            startActivity(intent)
                            finish()
                        },
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        Text(text = "Назад")
                    }
                }
            }
        }
    }
}
