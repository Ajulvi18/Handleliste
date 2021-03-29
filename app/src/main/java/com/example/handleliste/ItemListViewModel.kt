package com.example.handleliste

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.handleliste.data.ListItem
import com.example.handleliste.data.fromJson
import com.example.handleliste.data.subList
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import kotlin.random.Random

class ItemListViewModel(val dataSource: Datasource, context: Context) : ViewModel() {

    val listLiveData = dataSource.getListOfLists()
    val storage = Firebase.storage

    fun insertItem(itemName: String?, context: Context) {
        if (itemName == null) {
            return
        }

        val newItemList = subList(
                Random.nextLong(),
                emptyList<ListItem>(),
                itemName
        )

        dataSource.addNewList(newItemList, context)
    }

    fun getData(context: Context) {
        var storageRef = storage.reference
        var jsonRef = storageRef.child("Lists.json")
        val filename = "Lists"
        val file = File(context.filesDir, filename)
        jsonRef.getFile(file).addOnSuccessListener {
            val content = context.openFileInput(filename).bufferedReader().useLines { lines ->
                lines.fold("") { some, text ->
                    "$some\n$text"
                }
            }
            val list = fromJson(content)
            dataSource.update(list.list)

        }.addOnFailureListener {
            // Handle any errors
        }
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