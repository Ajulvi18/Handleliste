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
import com.example.handleliste.data.subList
import com.example.handleliste.databinding.ActivityMainBinding
import com.example.handleliste.listdetail.ListDetailActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


const val LIST_ID = "list id"

class MainActivity : AppCompatActivity() {

    private val newItemActivityRequestCode = 1
    private val itemListViewModel by viewModels<ItemListViewModel> {
        ItemListViewModelFactory(this)
    }
    lateinit var storage: FirebaseStorage
    private lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    private lateinit var addListName: TextInputEditText

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

        val itemAdapter = ItemAdapter { subList -> onListClick(subList) }
        val recyclerView: RecyclerView = findViewById(R.id.ItemCycler)
        recyclerView.adapter = itemAdapter
        itemAdapter.submitList(emptyList<subList>())


        itemListViewModel.listLiveData.observe(this, {
            it?.let {
                itemAdapter.submitList(it as MutableList<subList>)
            }
        })

        itemListViewModel.getData(this)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
        addListName = findViewById(R.id.add_list_name)

    }

    override fun onResume(){
        super.onResume()
        itemListViewModel.updateProgress()
        val itemAdapter = ItemAdapter { subList -> onListClick(subList) }
        val recyclerView: RecyclerView = findViewById(R.id.ItemCycler)
        recyclerView.adapter = itemAdapter
        itemAdapter.submitList(emptyList<subList>())


        itemListViewModel.listLiveData.observe(this, {
            it?.let {
                itemAdapter.submitList(it as MutableList<subList>)
            }
        })

        itemListViewModel.getData(this)
    }

    private fun onListClick(sublist: subList) {
        val intent = Intent(this, ListDetailActivity()::class.java)
        intent.putExtra(LIST_ID, sublist.id)
        startActivity(intent)
    }

    private fun fabOnClick() {

        if (addListName.text.isNullOrEmpty()) {
            return
        } else {
            val listName = addListName.text.toString()
            itemListViewModel.insertItem(listName, this)
        }
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