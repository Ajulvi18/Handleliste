package com.example.handleliste

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

const val LIST_NAME = "name"

class AddNewList : AppCompatActivity() {
    private lateinit var addListName: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_list)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addFlower()
        }
        addListName = findViewById(R.id.add_list_name)
    }


    private fun addFlower() {
        val resultIntent = Intent()

        if (addListName.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = addListName.text.toString()
            val description = "described"
            resultIntent.putExtra(LIST_NAME, name)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}