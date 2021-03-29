package com.example.handleliste.data
import com.google.gson.annotations.SerializedName
import com.google.gson.Gson


data class ListItem(
    @SerializedName("name")
    val name:String
)

data class subList(
    @SerializedName("id")
    val id: Long,
    @SerializedName("array")
    var array: List<ListItem>? = null,
    @SerializedName("listname")
    val listname:String,
)

data class Lister(
    @SerializedName("list")
    var list: List<subList>
)

public fun fromJson(json:String):Lister{
    return Gson().fromJson<Lister>(json, Lister::class.java)
}

public fun toJson(example: Lister):String{
    return Gson().toJson(example)
}
