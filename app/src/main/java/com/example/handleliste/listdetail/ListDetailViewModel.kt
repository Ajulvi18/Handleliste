package com.example.handleliste.listdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.handleliste.Datasource
import com.example.handleliste.data.ListItem
import com.example.handleliste.data.subList
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlin.random.Random

class ListDetailViewModel(val dataSource: Datasource, context: Context) : ViewModel() {

    val listLiveData = dataSource.getListOfLists()
    val storage = Firebase.storage

    fun getListForId(id: Long) : subList? {
        return dataSource.getListFromId(id)
    }
//TODO: fix insert Item
    fun insertItem(itemName: String?, context: Context, id: Long) {
        if (itemName == null) {
            return
        }
        val newListItem = ListItem(
            itemName
        )
        var currentList = getListForId(id)
        var updatedList = currentList?.array?.toMutableList()
        if (updatedList != null) {
            updatedList.add(0,newListItem)
        }
    lateinit var updatedSubList:subList
        if (currentList != null) {
            updatedSubList = subList(
                currentList.id,
                updatedList,
                currentList.listname
            )
        }

        dataSource.updateList(currentList, updatedSubList, context)
    }
}

class ListDetailViewModelFactory (private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListDetailViewModel::class.java)) {
            @Suppress("UNCHECK_CAST")
            return ListDetailViewModel(
                dataSource =  Datasource.getDataSource(context.resources),
                context = context
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}