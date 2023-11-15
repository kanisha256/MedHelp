package com.example.medhelp


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),

                ) {
                val db = Firebase.firestore
                var fio by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier

                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Вход",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .align(CenterHorizontally)

                    )

                    OutlinedTextField(
                        value = fio,
                        onValueChange = { fio = it },
                        label = { Text("ФИО") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    var password by rememberSaveable { mutableStateOf("") }
                    var passwordVisibility by remember { mutableStateOf(false) }
                    val icon = if (passwordVisibility)
                        painterResource(id = R.drawable.baseline_visibility)
                    else
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    OutlinedTextField(
                        value = password,
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { password = it
                        },
                        placeholder = { Text(text = "Password") },
                        label = { Text(text = "Password") },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            }) {
                                Icon(
                                    painter = icon,
                                    contentDescription = "Visibility Icon"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisibility) VisualTransformation.None
                        else
                            PasswordVisualTransformation()
                    )

                    Button(
                        onClick = {
                            if (fio.isEmpty()) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Enter FIO",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                return@Button
                            }

                            if (password.isEmpty()) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Enter password",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                return@Button
                            }
                            Singleton.fio = fio
                            val prov = db.collection("MedSestry")
                            prov
                                .whereEqualTo("fio", fio)
                                .whereEqualTo("password", password)
                                .get()
                                .addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        val intent = Intent(this@MainActivity, Home::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    if (documents.isEmpty) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Try again",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                .addOnFailureListener {
                                }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(text = "Вход")
                    }
                }
            }
        }
    }
}