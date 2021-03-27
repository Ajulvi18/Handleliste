package com.example.handleliste

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.handleliste.data.ListItem
import com.example.handleliste.data.itemsList

class Datasource(resources: Resources) {
    private val initialItemList = itemsList(resources)
    private val listItemLiveData = MutableLiveData(initialItemList)


    fun addNewList(listItem: ListItem){
        val currentList = listItemLiveData.value
        if (currentList == null) {
            listItemLiveData.postValue(listOf(listItem))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, listItem)
            listItemLiveData.postValue(updatedList)
        }
    }

    fun removeList(listItem: ListItem) {
        val currentList = listItemLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(listItem)
            listItemLiveData.postValue(updatedList)
        }
    }

    fun getListFromId(id: Long): ListItem? {
        listItemLiveData.value?.let { lists ->
            return lists.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getListOfLists(): LiveData<List<ListItem>> {
        return listItemLiveData
    }


    companion object {
        private var INSTANCE: Datasource? = null

        fun getDataSource(resources: Resources): Datasource {
            return synchronized(Datasource::class) {
                val newInstance = INSTANCE ?: Datasource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}


