package com.projects.rickandmorty

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.projects.rickandmorty.databinding.ActivityMainBinding
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val viewModel : SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.refreshCharacter(54)
        viewModel.characterByIdLiveData.observe(this){response->
            if(response == null){
                Toast.makeText(
                    this@MainActivity,
                    "Unsuccessful network call",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }

                binding.tvName.text = response.name
                binding.tvOriginValue.text = response.origin.name
                binding.tvStatus.text = response.status
                binding.tvSpeciesValue.text = response.species


                if(response.gender.equals("male",true)){
                    binding.ivGender.setImageResource(R.drawable.male)
                }else{
                    binding.ivGender.setImageResource(R.drawable.female)
                }

                Picasso.get().load(response.image).into(binding.headerImageView)

        }

        //why repo class- in order to do below fun call, we need to call it from a suspend function- uncomment and check the error
//        NetworkLayer.apiClient.getCharacterById(54)


//        //using enque to avoid crash since we are on the UI thread
//        //creating an anonymous inner class that implements Interface Callback's methods
//        rickAndMortyService.getCharacterById(10).enqueue(object: Callback<GetCharacterByIdResponse>{
//            override fun onResponse(call: Call<GetCharacterByIdResponse>, response: retrofit2.Response<GetCharacterByIdResponse>) {
//                Log.i("MainActivity", response.toString())
//
//                val body = response.body()!!
//                val name = body.name
//                binding.tvName.text = name
//                binding.tvOriginValue.text = body.origin.name
//                binding.tvStatus.text = body.status
//                binding.tvSpeciesValue.text = body.species
//
//
//                if(body.gender.equals("male",true)){
//                    binding.ivGender.setImageResource(R.drawable.male)
//                }else{
//                    binding.ivGender.setImageResource(R.drawable.female)
//                }
//
//                Picasso.get().load(body.image).into(binding.headerImageView)
//            }
//
//            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
//                Log.i("MainActivity",t.message ?:"Null message")
//            }
//
//        })
    }
}