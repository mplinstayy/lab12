package com.example.jsonphonebook

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddContacts : AppCompatActivity() {

    private lateinit var editName:EditText
    private lateinit var editNumber:EditText
    private lateinit var btnSaveContact:Button
    private lateinit var tv:TextView
    private val phoneBook: MutableList<PhoneBook> = mutableListOf()
    private var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contacts)
        getPhoneBook()

        index = intent.getIntExtra("index", -1)

        btnSaveContact = findViewById(R.id.buttonAdd)
        editName = findViewById(R.id.editTextTextPersonName)
        editNumber = findViewById(R.id.editTextNumber)
        tv = findViewById(R.id.textView2)

        if (index != -1){
            tv.text = "Изменить контакт"
            btnSaveContact.text = "Изменить"
            editName.setText(phoneBook[index].name)
            editNumber.setText(phoneBook[index].phone)
        }

        //val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)

        btnSaveContact.setOnClickListener {
            if (index == -1){
                val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                phoneBook.add(PhoneBook(editName.text.toString(), editNumber.text.toString()))
                preferences.edit {
                this.putString("str", Gson().toJson(phoneBook).toString())
                }
            }else{
                phoneBook[index].name = editName.text.toString()
                phoneBook[index].phone = editNumber.text.toString()
                val preferences = getSharedPreferences("prefs", MODE_PRIVATE)
                preferences.edit{
                    this.putString("str", Gson().toJson(phoneBook).toString())
                }
            }
            editName.setText("")
            editNumber.setText("")
        }
    }

    private fun getPhoneBook() {
        val preferences = getSharedPreferences("prefs", MODE_PRIVATE)
        if (preferences.contains("str")){
            val str = preferences.getString("str", "")
            phoneBook.addAll(Gson().fromJson(str, object : TypeToken<MutableList<PhoneBook>>(){}.type))
        }
    }
}