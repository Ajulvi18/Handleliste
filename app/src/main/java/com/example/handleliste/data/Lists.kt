package com.example.handleliste.data

import android.content.res.Resources
import com.example.handleliste.R

fun itemsList(resources: Resources): List<ListItem> {
    return listOf(
            ListItem(
                    id = 1,
                    name = resources.getString(R.string.List1_name),
                    description = "desk"
            )
    )
}