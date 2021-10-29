package ru.princesch.testbbchars.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterAPI {
    @GET("characters")
    fun getCharacters():Call<List<CharacterDTO>>

    @GET("characters/{char_id}")
    fun getDetail(
       @Path("char_id") id: Int
    ):Call<List<CharacterDTO>>
}