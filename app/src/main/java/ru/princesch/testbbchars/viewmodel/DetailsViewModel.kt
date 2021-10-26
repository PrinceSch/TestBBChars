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

class DetailsViewModel(private val dataSource: RemoteDataSource) : ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getData(): LiveData<AppState> = liveDataToObserve
    fun getDataFromServer(id: Int,) {
        liveDataToObserve.value = AppState.Loading
        dataSource.getCharacter(id, detailCallback)
    }

    private val detailCallback = object : Callback<CharacterDTO> {
        @Throws(IOException::class)
        override fun onResponse(
            call: Call<CharacterDTO>,
            response: Response<CharacterDTO>
        ) {
            val serverResponse: CharacterDTO? = response.body()
            liveDataToObserve.postValue(
                (if (response.isSuccessful && serverResponse != null) {
                    AppState.DetailSuccess(convertDtoToModel(serverResponse))
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }) as AppState?
            )
        }

        override fun onFailure(call: Call<CharacterDTO>, textError: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(textError.message)))
        }

        private fun convertDtoToModel (charDTO: CharacterDTO): Character{
            return Character(
                charDTO.char_id, charDTO.name, charDTO.birthday, charDTO.occupation, charDTO.img,
                charDTO.status, charDTO.appearance, charDTO.nickname, charDTO.portrayed
            )
        }
    }

}