package com.example.handleliste

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.handleliste.data.ListItem
import kotlin.random.Random

class ItemListViewModel(val dataSource: Datasource, context: Context) : ViewModel() {

    val listLiveData = dataSource.getListOfLists()

    fun insertItem(itemName: String?, context: Context) {
        if (itemName == null) {
            return
        }

        val newItemList = ListItem(
            Random.nextLong(),
            itemName,
            "desk"
        )

        dataSource.addNewList(newItemList, context)
    }
}

class ItemListViewModelFactory (private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemListViewModel::class.java)) {
            @Suppress("UNCHECK_CAST")
            return ItemListViewModel(
                dataSource =  Datasource.getDataSource(context.resources),
                context = context
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}