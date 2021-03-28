package com.example.handleliste

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.viewModels
import com.example.handleliste.data.ListItem
import com.example.handleliste.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


const val ITEM_ID = "item id"

class MainActivity : AppCompatActivity() {

    private val newItemActivityRequestCode = 1
    private val itemListViewModel by viewModels<ItemListViewModel> {
        ItemListViewModelFactory(this)
    }
    lateinit var storage: FirebaseStorage
    private lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var auth: FirebaseAuth

        auth = Firebase.auth
        auth.signInAnonymously().addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        storage = Firebase.storage

        val itemAdapter = ItemAdapter { ListItem -> onListClick(ListItem) }
        val data = Datasource.INSTANCE?.initialize(this)
        val recyclerView: RecyclerView = findViewById(R.id.ItemCycler)
        recyclerView.adapter = itemAdapter
        itemAdapter.submitList(emptyList<ListItem>())
        Datasource.INSTANCE?.onLiveData = {
            Datasource.INSTANCE?.listItemLiveData?.observe(this, {
                it?.let {
                    itemAdapter.submitList(it as MutableList<ListItem>)
                }
            })
        }



        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    private fun onListClick(listItem: ListItem) {
        val toast = Toast.makeText(applicationContext, "item clicked", Toast.LENGTH_LONG)
        toast.show()
    }

    private fun fabOnClick() {
        val intent = Intent(this, AddNewList::class.java)
        startActivityForResult(intent, newItemActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        /* Inserts flower into viewModel. */
        if (requestCode == newItemActivityRequestCode && resultCode == Activity.RESULT_OK) {
            if (requestCode == newItemActivityRequestCode && resultCode == Activity.RESULT_OK) {
                intentData?.let { data ->
                    val flowerName = data.getStringExtra(LIST_NAME)

                    itemListViewModel.insertItem(flowerName, this)
                }
            }
        }
    }
}