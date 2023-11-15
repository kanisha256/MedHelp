package com.example.medhelp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
class Pacienty : ComponentActivity(){
    var recview: RecyclerView? = null
    var button: Button? = null
    var button2: Button? = null
    var datalist: ArrayList<Model?>? = null
    var db: FirebaseFirestore? = null
    var adapter: Adapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recview = findViewById<View>(R.id.recview) as RecyclerView
        button = findViewById<Button>(R.id.button)
        button2 = findViewById<Button>(R.id.backbutton)
        button2!!.text = "Назад"
        button2!!.setBackgroundColor(Color.TRANSPARENT)
        button!!.text = "Добавить пациента"
        button!!.setBackgroundColor(Color.TRANSPARENT)
        recview!!.layoutManager = LinearLayoutManager(this)
        datalist = ArrayList()
        adapter = Adapter(datalist)
        recview!!.adapter = adapter
        db = FirebaseFirestore.getInstance()
        db!!.collection(Singleton.fio).get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                val list = queryDocumentSnapshots.documents
                for (d in list) {
                    val obj = d.toObject(Model::class.java)
                    datalist!!.add(obj)
                }
                adapter!!.notifyDataSetChanged()
            }
        adapter?.setOnItemClickListener(object : Adapter.OnItemClickListener {
            override fun onItemClick(item: Model?,fiopac:String,palata:String) {
                val intent = Intent(this@Pacienty, Medecine::class.java)
                intent.putExtra("fiopac",fiopac)
                Singleton.pacfio = fiopac
                intent.putExtra("palata",palata)
                startActivity(intent)
                finish()
            }

        })
        button!!.setOnClickListener {
            val intent = Intent(this@Pacienty, AddPac::class.java)
            startActivity(intent)
            finish()
        }
        button2!!.setOnClickListener {
            val intent = Intent(this@Pacienty, Home::class.java)
            startActivity(intent)
            finish()
        }

    }
}