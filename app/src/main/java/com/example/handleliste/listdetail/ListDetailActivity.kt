package com.example.handleliste.listdetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.handleliste.*
import com.example.handleliste.data.ListItem
import com.example.handleliste.data.subList
import com.google.android.material.textfield.TextInputEditText

class ListDetailActivity : AppCompatActivity() {
    private val listDetailViewModel by viewModels<ListDetailViewModel> {
        ListDetailViewModelFactory(this)
    }
    private lateinit var addItemName: TextInputEditText
    lateinit var currentList:subList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)
        var currentListId: Long? = null

        val listName: TextView = findViewById(R.id.list_detail_name)
        val listProgress: ProgressBar = findViewById(R.id.progressBar2)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentListId = bundle.getLong(LIST_ID)
        }

        val listDetailAdapter = ListDetailAdapter { ListItem, View -> onListClick(ListItem, View) }
        val recyclerView: RecyclerView = findViewById(R.id.ListDetailCycler)
        recyclerView.adapter = listDetailAdapter

        currentListId?.let {
            currentList = listDetailViewModel.getListForId(it)!!
            listName.text = currentList?.listname
            if (currentList != null) {
                listProgress.progress = currentList.progress
            }
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
        listDetailViewModel.listLiveData.observe(this, {
            it?.let {
                listProgress.progress = currentList.progress
            }
        })
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
        listDetailViewModel.updateProgress()

        listDetailViewModel.sync(this)
    }
    private fun fabOnClick(id:Long, context: Context) {
        if (addItemName.text.isNullOrEmpty()) {
            return
        } else {
            val ItemName =  addItemName.text.toString()
            listDetailViewModel.insertItem(ItemName, context, id)
            listDetailViewModel.updateProgress()
            finish();
            startActivity(getIntent());
        }
    }
}