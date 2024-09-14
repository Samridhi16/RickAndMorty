package com.projects.rickandmorty

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.projects.rickandmorty.databinding.ActivityMainBinding
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val rickAndMortyService: RickAndMortyService = retrofit
                                                        .create(RickAndMortyService::class.java)

        //using enque to avoid crash since we are on the UI thread
        //creating an anonymous inner class that implements Interface Callback's methods
        rickAndMortyService.getCharacterById().enqueue(object: Callback<Any>{
            override fun onResponse(call: Call<Any>, response: retrofit2.Response<Any>) {
                Log.i("MainActivity", response.toString())
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i("MainActivity",t.message ?:"Null message")
            }

        })
    }
}