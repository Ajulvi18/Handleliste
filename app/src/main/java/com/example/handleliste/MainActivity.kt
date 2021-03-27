package com.example.handleliste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.viewModels
import com.example.handleliste.data.ListItem
import com.example.handleliste.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

const val ITEM_ID = "item id"

class MainActivity : AppCompatActivity() {

    private val newItemActivityRequestCode = 1
    private val itemListViewModel by viewModels<ItemListViewModel> {
        ItemListViewModelFactory(this)
    }
    lateinit var storage: FirebaseStorage
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        storage = Firebase.storage

        val itemAdapter = ItemAdapter{ ListItem -> onListClick(ListItem)}

        val recyclerView: RecyclerView = findViewById(R.id.ItemCycler)
        recyclerView.adapter = itemAdapter

        itemListViewModel.listLiveData.observe(this,{
            it?.let {
                itemAdapter.submitList(it as MutableList<ListItem>)
            }
        })
    }

    private fun onListClick(listItem:ListItem) {
        val toast = Toast.makeText(applicationContext, "item clicked", Toast.LENGTH_LONG)
        toast.show()
    }
}