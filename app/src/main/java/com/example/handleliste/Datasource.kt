package com.example.handleliste


import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.example.handleliste.data.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File


class Datasource(resources: Resources) {

    var onLiveData: ((LiveData<List<subList>>) -> Unit)? = null



    private lateinit var queue: RequestQueue

    private val initialItemList = itemsList(resources)
    val listItemLiveData = MutableLiveData(initialItemList)
    val storage = Firebase.storage

    fun update(updatedList: MutableList<subList>?){
        listItemLiveData.postValue(updatedList)
    }

    fun addNewList(listItem: subList, context: Context){

        val currentList = listItemLiveData.value
        var storageRef = storage.reference
        var jsonRef = storageRef.child("Lists.json")
        if (currentList == null) {
            listItemLiveData.postValue(mutableListOf(listItem))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, listItem)

            val json = toJson(Lister(updatedList))
            val filename = "Lists.json"
            val file = File(context.filesDir, filename)
            context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
            val uploadTask = jsonRef.putFile(Uri.fromFile(file))
            listItemLiveData.postValue(updatedList)
        }
    }

    fun removeList(listItem: subList?) {
        val currentList = listItemLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(listItem)
            listItemLiveData.postValue(updatedList)
        }
    }

    fun getListFromId(id: Long): subList? {
        listItemLiveData.value?.let { lists ->
            return lists.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getListOfLists(): MutableLiveData<MutableList<subList>> {
        return listItemLiveData
    }

    fun updateList(listToUpdate: subList, itemToAdd: ListItem, context: Context ) {
        lateinit var json:String
        var storageRef = storage.reference
        var jsonRef = storageRef.child("Lists.json")

        listToUpdate.array?.add(itemToAdd)

        val updatedList = listItemLiveData.value

        if (updatedList != null){
            json = toJson(Lister(updatedList))
        }
        val filename = "Lists.json"
        val file = File(context.filesDir, filename)
        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
        val uploadTask = jsonRef.putFile(Uri.fromFile(file))
        listItemLiveData.postValue(updatedList)

    }

    companion object {
        var INSTANCE: Datasource? = null

        fun getDataSource(resources: Resources): Datasource {
            return synchronized(Datasource::class) {
                val newInstance = INSTANCE ?: Datasource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}


