package com.example.handleliste

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handleliste.data.ListItem
import com.example.handleliste.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {

    lateinit var storage: FirebaseStorage
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        storage = Firebase.storage

        val listOfItems = Datasource( this).getItemList()

        val recyclerView: RecyclerView = findViewById(R.id.ItemCycler)
        recyclerView.adapter = ItemAdapter(listOfItems)
    }
}