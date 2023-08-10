package com.example.api_mark_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL
import java.util.Locale
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var searchView : SearchView

    private lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView = findViewById(R.id.searchUser)

        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userList = arrayListOf<User>()
        userAdapter = UserAdapter(userList)
        userRecyclerView.adapter = userAdapter



        //second thread to connect to internet
        val executor = Executors.newSingleThreadExecutor()
        executor.execute{
            val url = URL("https://wordapidata.000webhostapp.com/Dis.php")
            val json = url.readText()
            val parsedUserList: List<User> = Gson().fromJson(json, object : TypeToken<List<User>>() {}.type)
            userList.clear()
            userList.addAll(parsedUserList)

            Handler(Looper.getMainLooper()).post{
                Log.i("Add new plan", "Plain java vars $json")
                Log.i("Add new plan", "Converted json $userList")

                userAdapter.notifyDataSetChanged()
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

    }

    private fun filterList(query: String?) {
            if (query != null){
                val filterList = ArrayList<User>()
                for(i in userList){
                    if(i.Name.lowercase(Locale.ROOT).contains(query)){
                        filterList.add(i)
                    }
                }
                if(filterList.isEmpty()){
                    Toast.makeText(this,"user not found",Toast.LENGTH_SHORT).show()
                }else{
                    userAdapter.setFilteredist(filterList)
                }
            }
    }
}