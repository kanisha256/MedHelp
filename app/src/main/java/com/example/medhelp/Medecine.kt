package com.example.medhelp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class Medecine : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val fio = intent.getStringExtra("fiopac")
            val palata = intent.getStringExtra("palata")
            var lec by remember { mutableStateOf("") }
            var time by remember { mutableStateOf("") }
            var db = FirebaseFirestore.getInstance()
            val itemList = remember { mutableStateListOf<String>() }

            // Получение данных из Firestore
            LaunchedEffect(true) {
                val collectionRef = db.collection(fio.toString())
                val querySnapshot = collectionRef.get().await()

                for (document in querySnapshot.documents) {
                    val lec = document.getString("lec")
                    val time = document.getString("time")
                    lec?.let {
                        itemList.add(it + "      ")
                    }

                    time?.let {
                        itemList.add("Время: " + it + "\n")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(this@Medecine, History::class.java)
                            intent.putExtra("fio",fio.toString())
                            startActivity(intent)
                            finish()
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "История лекарств")
                    }
                }

                Text(
                    text = "ФИО: $fio Палата: №$palata",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TextField(
                    value = lec,
                    onValueChange = { lec = it },
                    label = { Text("Введите лекарства") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Введите время принятия") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        db.collection(fio.toString()).document(lec).set(lecar(lec,time))
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Сохранить")
                }

                Button(
                    onClick = {
                        val intent = Intent(this@Medecine, Pacienty::class.java)
                        startActivity(intent)
                        finish()
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Назад")
                }

                Text(
                    text = "Список лекарств:",
                    fontSize = 36.sp,
                    modifier = Modifier.padding(top = 20.dp)
                )

                val clickableTextStyle = TextStyle(
                    fontSize = 26.sp,
                    textDecoration = TextDecoration.None, // Устанавливаем отсутствие подчеркивания
                    color = Color.Black // Устанавливаем черный цвет текста
                )

                    val text = itemList.joinToString("")
                    ClickableText(
                        text = AnnotatedString(text),
                        modifier = Modifier.padding(top = 20.dp),
                        style = clickableTextStyle,
                        onClick = { offset ->
                            val clickedText = getClickedText(offset, text)
                            db.collection(fio.toString() + "History").document().set(mapOf("lec" to clickedText))
                        }
                    )
            }
        }
    }

    private fun getClickedText(offset: Int, text: String): String {
        val regex = Regex("([^\\n]+)")
        val matchResult = regex.findAll(text)
        matchResult.forEach { result ->
            val range = result.range
            if (offset in range) {
                return result.value
            }
        }
        return ""
    }

}

