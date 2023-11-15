package com.example.medhelp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddPac: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreeFieldsScreen()
        }
    }

    @Composable
    fun ThreeFieldsScreen() {
        var name by remember { mutableStateOf("") }
        var palata by remember { mutableStateOf("") }
        var db = FirebaseFirestore.getInstance()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("FIO") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = palata,
                onValueChange = { palata = it },
                label = { Text("Palata") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    db.collection(Singleton.fio).document(name).get().addOnSuccessListener {documents ->
                        db.collection(Singleton.fio).document(name).set(AddP(fiopac = name, palata = palata))
                        val intent = Intent(this@AddPac, Pacienty::class.java)
                        startActivity(intent)
                        finish()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Добавить")
            }
            Button(
                onClick = {
                        val intent = Intent(this@AddPac, Pacienty::class.java)
                        startActivity(intent)
                        finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Назад")
            }
        }
    }
}