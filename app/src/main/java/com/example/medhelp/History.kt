package com.example.medhelp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class History : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageListScreen()
        }
    }

    suspend fun getMessagesFromFirestore(): List<Lec> {
        val fio = intent.getStringExtra("fio")
        Toast.makeText(this@History,fio,Toast.LENGTH_SHORT).show()
        val firestore = FirebaseFirestore.getInstance()
        val messagesCollection = firestore.collection(fio+"History")
        val querySnapshot = messagesCollection.get().await()
        val messageList = mutableListOf<Lec>()
        for (document in querySnapshot.documents) {
            val hist = document.getString("lec") ?: ""
            val message = Lec(hist)
            messageList.add(message)
        }
        return messageList
    }

    @Composable
    fun MessageListScreen() {
        val messageList = remember { mutableStateListOf<Lec>() }

        LaunchedEffect(Unit) {
            val messages = getMessagesFromFirestore()
            messageList.addAll(messages)
        }

        val text = messageList.joinToString("\n")
        Text(
            text = text,
            fontSize = 36.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
    }

}