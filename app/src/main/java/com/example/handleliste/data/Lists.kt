package com.example.handleliste.data

import android.content.res.Resources
import com.example.handleliste.R

fun itemsList(resources: Resources): List<ListItem> {
    return listOf(
            ListItem(
                    id = 1,
                    name = resources.getString(R.string.List1_name),
                    description = resources.getString(R.string.flower1_description)
            ),
            ListItem(
                    id = 2,
                    name = resources.getString(R.string.List2_name),
                    description = resources.getString(R.string.flower2_description)
            ),
            ListItem(
                    id = 3,
                    name = resources.getString(R.string.List3_name),
                    description = resources.getString(R.string.flower3_description)
            ),
            ListItem(
                    id = 4,
                    name = resources.getString(R.string.List4_name),
                    description = resources.getString(R.string.flower4_description)
            ),
            ListItem(
                    id = 5,
                    name = resources.getString(R.string.List5_name),
                    description = resources.getString(R.string.flower5_description)
            ),
            ListItem(
                    id = 6,
                    name = resources.getString(R.string.List6_name),
                    description = resources.getString(R.string.flower6_description)
            ),
            ListItem(
                    id = 7,
                    name = resources.getString(R.string.List7_name),
                    description = resources.getString(R.string.flower7_description)
            ),
            ListItem(
                    id = 8,
                    name = resources.getString(R.string.List8_name),
                    description = resources.getString(R.string.flower8_description)
            ),
            ListItem(
                    id = 9,
                    name = resources.getString(R.string.List9_name),
                    description = resources.getString(R.string.flower9_description)
            ),
            ListItem(
                    id = 10,
                    name = resources.getString(R.string.List10_name),
                    description = resources.getString(R.string.flower10_description)
            ),
            ListItem(
                    id = 11,
                    name = resources.getString(R.string.List11_name),
                    description = resources.getString(R.string.flower11_description)
            )
    )
}