package com.example.handleliste.listdetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.handleliste.*
import com.example.handleliste.data.ListItem
import com.example.handleliste.data.subList
import com.example.handleliste.listdetail.ListDetailViewModel
import com.google.android.material.textfield.TextInputEditText

class ListDetailActivity : AppCompatActivity() {
    private val listDetailViewModel by viewModels<ListDetailViewModel> {
        ListDetailViewModelFactory(this)
    }
    private lateinit var addItemName: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        var currentListId: Long? = null

        val listName: TextView = findViewById(R.id.list_detail_name)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentListId = bundle.getLong(LIST_ID)
        }

        val listDetailAdapter = ListDetailAdapter { ListItem, View -> onListClick(ListItem, View) }
        val recyclerView: RecyclerView = findViewById(R.id.ListDetailCycler)
        recyclerView.adapter = listDetailAdapter

        currentListId?.let {
            val currentList = listDetailViewModel.getListForId(it)
            listName.text = currentList?.listname
            val aList = currentList?.array
            listDetailAdapter.submitList(aList)

            val fab: View = findViewById(R.id.addDetailFab)
            fab.setOnClickListener {
                if (currentList != null) {
                    fabOnClick(currentList.id, this)
                }
            }
        }
        addItemName = findViewById(R.id.add_item_name)
    }

    private fun onListClick(listItem: ListItem, itemView: View) {
        lateinit var checkBox: CheckBox
        if (listItem.completion == 1) {
            listItem.completion = 0
        } else {
            listItem.completion = 1
        }
        checkBox = itemView.findViewById(R.id.checkBox)
        checkBox.isChecked = listItem.completion != 0

        listDetailViewModel.sync(this)
    }
    private fun fabOnClick(id:Long, context: Context) {
        if (addItemName.text.isNullOrEmpty()) {
            return
        } else {
            val ItemName =  addItemName.text.toString()
            listDetailViewModel.insertItem(ItemName, context, id)
            finish();
            startActivity(getIntent());
        }
    }
}