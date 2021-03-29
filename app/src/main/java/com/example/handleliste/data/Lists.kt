package com.example.handleliste.data

import android.content.res.Resources
import com.example.handleliste.R

fun itemsList(resources: Resources): List<subList> {
    return listOf(
            subList(
                    array = emptyList<ListItem>(),
                    listname = resources.getString(R.string.List1_name)
            )
    )
}