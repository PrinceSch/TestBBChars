package ru.princesch.testbbchars.model

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val charAPI = Retrofit.Builder()
        .baseUrl("https://www.breakingbadapi.com/api/")
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        .build().create(CharacterAPI::class.java)

    fun getList(callback: Callback<List<CharacterDTO>>){
        charAPI.getCharacters().enqueue(callback)
    }

    fun getCharacter(id: Int, callback: Callback<CharacterDTO>){
        charAPI.getDetail(id).enqueue(callback)
    }

}