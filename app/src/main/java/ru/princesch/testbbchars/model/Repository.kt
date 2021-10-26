package ru.princesch.testbbchars.model

import retrofit2.Callback

class Repository(private val remoteDataSource: RemoteDataSource) {

    fun getListOfCharacters(callback: Callback<List<CharacterDTO>>){
        remoteDataSource.getList(callback)
    }

    fun getDetainedCharacter(id: Int, callback: Callback<CharacterDTO>){
        remoteDataSource.getCharacter(id, callback)
    }

}