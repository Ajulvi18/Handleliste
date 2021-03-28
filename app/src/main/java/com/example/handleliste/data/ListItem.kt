package com.example.handleliste.data
import com.google.gson.annotations.SerializedName
import com.google.gson.Gson


data class ListItem(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name:String,
    @SerializedName("description")
    val description: String
)

data class Example(
    @SerializedName("array")
    var array: List<ListItem>? = null
)


public fun fromJson(json:String):Example{
    return Gson().fromJson<Example>(json, Example::class.java)
}

public fun toJson(example: Example):String{
    return Gson().toJson(example)
}
