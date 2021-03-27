package com.example.handleliste

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.handleliste.data.ListItem
import kotlin.random.Random

class ItemListViewModel(val dataSource: Datasource) : ViewModel() {

    val listLiveData = dataSource.getListOfLists()

    fun insetItem(itemName: String?, itemDescription: String?) {
        if (itemName == null || itemDescription == null) {
            return
        }

        val newItemList = ListItem(
            Random.nextLong(),
            itemName,
            itemDescription
        )

        dataSource.addNewList(newItemList)
    }
}

class ItemListViewModelFactory (private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemListViewModel::class.java)) {
            @Suppress("UNCHECK_CAST")
            return ItemListViewModel(
                dataSource =  Datasource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}