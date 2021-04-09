package com.example.handleliste.data

import android.content.res.Resources
import com.example.handleliste.R

fun itemsList(resources: Resources): MutableList<subList> {
    return mutableListOf(
            subList(
                    1,
                    array = mutableListOf<ListItem>(),
                    listname = resources.getString(R.string.List1_name)
            )
    )
}