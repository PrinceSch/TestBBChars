package ru.princesch.testbbchars.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterAPI {
    @GET("/characters")
    fun getCharacters():Call<List<CharacterDTO>>

    @GET("/characters/{id}")
    fun getDetail(
        @Path("id") id: Int
    ):Call<CharacterDTO>
}