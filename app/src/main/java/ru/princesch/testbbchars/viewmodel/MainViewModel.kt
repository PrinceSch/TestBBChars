package ru.princesch.testbbchars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.princesch.testbbchars.model.Character
import ru.princesch.testbbchars.model.CharacterDTO
import ru.princesch.testbbchars.model.RemoteDataSource
import java.io.IOException

const val SERVER_ERROR = "Ошибка сервера"

class MainViewModel(private val dataSource: RemoteDataSource) : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getData(): LiveData<AppState> = liveDataToObserve
    private fun getDataFromServer() {
        liveDataToObserve.value = AppState.Loading
        dataSource.getList(mainCallback)
    }

    private val mainCallback = object : Callback<List<CharacterDTO>> {
        @Throws(IOException::class)
        override fun onResponse(
            call: Call<List<CharacterDTO>>,
            response: Response<List<CharacterDTO>>
        ) {
            val serverResponse: List<CharacterDTO>? = response.body()
            liveDataToObserve.postValue(
                (if (response.isSuccessful && serverResponse != null) {
                    AppState.CharactersSuccess(convertDtoToModel(serverResponse))
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }) as AppState?
            )
        }

        override fun onFailure(call: Call<List<CharacterDTO>>, textError: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(textError.message)))
        }

        private fun convertDtoToModel (charsDTO: List<CharacterDTO>): List<Character>{
            var list = mutableListOf<Character>()
            charsDTO.forEach {
                list.add(
                    Character(it.char_id, it.name, it.birthday, it.occupation, it.img, it.status,
                it.appearance, it.nickname, it.portrayed)
                )
            }
            return list
        }
    }
}