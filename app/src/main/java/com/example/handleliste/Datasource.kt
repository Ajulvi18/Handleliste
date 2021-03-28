package com.example.handleliste


import android.content.Context
import android.content.res.Resources
import android.net.Uri
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

    var onLiveData: ((LiveData<List<ListItem>>) -> Unit)? = null



    private lateinit var queue: RequestQueue

    private val initialItemList = itemsList(resources)
    val listItemLiveData = MutableLiveData(initialItemList)
    val storage = Firebase.storage

    fun initialize(context: Context){
        var storageRef = storage.reference
        val filename = "Lists"
        val file = File(context.filesDir, filename)
        storageRef.getFile(file).addOnSuccessListener {
            val content = context.openFileInput(filename).bufferedReader().useLines { lines ->
                lines.fold("") { some, text ->
                    "$some\n$text"
                }
            }
            val list = fromJson(content)
            listItemLiveData.postValue(list.array)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    fun addNewList(listItem: ListItem, context: Context){

        val currentList = listItemLiveData.value
        var storageRef = storage.reference
        var jsonRef = storageRef.child("Lists.json")
        if (currentList == null) {
            listItemLiveData.postValue(listOf(listItem))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, listItem)

            val json = toJson(Example(updatedList))
            val filename = "Lists.json"
            val file = File(context.filesDir, filename)
            context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
            val uploadTask = jsonRef.putFile(Uri.fromFile(file))
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


