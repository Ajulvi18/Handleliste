package com.example.handleliste.data
import com.google.gson.annotations.SerializedName
import com.google.gson.Gson


data class ListItem(
    @SerializedName("name")
    val name:String,
    @SerializedName("completion")
    var completion:Int = 0
)

data class subList(
    @SerializedName("id")
    val id: Long,
    @SerializedName("array")
    var array: MutableList<ListItem>? = null,
    @SerializedName("listname")
    val listname:String,
    @SerializedName("progress")
    var progress:Int = 0
)

data class Lister(
    @SerializedName("list")
    var list: MutableList<subList>
)

public fun fromJson(json:String):Lister{
    return Gson().fromJson<Lister>(json, Lister::class.java)
}

public fun toJson(example: Lister):String{
    return Gson().toJson(example)
}
