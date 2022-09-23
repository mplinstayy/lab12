package com.example.jsonphonebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Results : AppCompatActivity() {

    private val phoneBook: MutableList<PhoneBook> = mutableListOf()
    private lateinit var rv: RecyclerView
    private var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        getPhoneBook()
        val adapter = ContactRVAdapter(this, phoneBook)

        val rvListener = object : ContactRVAdapter.ItemClickListener{
            override fun onItemClick(view: View?, position: Int) {
                val intent = Intent(this@Results, AddContacts::class.java)
                intent.putExtra("index", position)
                index = position
                startActivity(intent)
            }
        }
        adapter.setOnClickListener(rvListener)
        rv = findViewById(R.id.rvContacts)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }

    private fun getPhoneBook() {
        val preferences = getSharedPreferences("prefs", MODE_PRIVATE)
        if (preferences.contains("str")){
            val str = preferences.getString("str", "")
            phoneBook.addAll(Gson().fromJson(str, object : TypeToken<MutableList<PhoneBook>>(){}.type))
        }
    }

    override fun onResume(){
        super.onResume()
        if (index != -1){
            phoneBook.clear()
            getPhoneBook()
            rv.adapter?.notifyItemChanged(index)
        }
    }

    //то же самое что и getPhoneBook
    /*private fun getContacts(){
        val pref= getSharedPreferences("prefs", MODE_PRIVATE)
        var json:String=""
        if (!pref.contains("str")){
            return
        } else {
            json = pref.getString("str","").toString()
        }
        val tempList = Gson().fromJson<List<PhoneBook>>(json, object: TypeToken<List<PhoneBook>>(){}.type)

    }*/
}