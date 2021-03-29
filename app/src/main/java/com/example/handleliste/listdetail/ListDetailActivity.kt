package com.example.handleliste.listdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.handleliste.*
import com.example.handleliste.data.ListItem
import com.example.handleliste.data.subList
import com.example.handleliste.listdetail.ListDetailViewModel

class ListDetailActivity : AppCompatActivity() {
    private val listDetailViewModel by viewModels<ListDetailViewModel> {
        ListDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        var currentListId: Long? = null

        val listName: TextView = findViewById(R.id.list_detail_name)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentListId = bundle.getLong(LIST_ID)
        }

        currentListId?.let {
            val currentList = listDetailViewModel.getListForId(it)
            listName.text = currentList?.listname
        }
/*
        val listDetailAdapter = ListDetailAdapter { ListItem -> onListClick(ListItem) }
        val recyclerView: RecyclerView = findViewById(R.id.ItemCycler)
        recyclerView.adapter = listDetailAdapter


        listDetailViewModel.listLiveData.observe(this, {
            it?.let {
                listDetailAdapter.submitList(it as MutableList<ListItem>)
            }
        })
*/
    }

    private fun onListClick(listItem: ListItem) {
        // do something
        return
    }
}