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
import ru.princesch.testbbchars.model.Repository
import java.io.IOException

class DetailsViewModel(
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = Repository(RemoteDataSource())
) : ViewModel() {

    fun getData(): LiveData<AppState> = liveDataToObserve
    fun getDataFromServer(id: Int) {
        liveDataToObserve.value = AppState.Loading
        repository.getDetailedCharacter(id, detailCallback)
    }

    private val detailCallback = object : Callback<List<CharacterDTO>> {
        @Throws(IOException::class)
        override fun onResponse(
            call: Call<List<CharacterDTO>>,
            response: Response<List<CharacterDTO>>
        ) {
            val serverResponse: List<CharacterDTO>? = response.body()
            liveDataToObserve.postValue(
                (if (response.isSuccessful && serverResponse != null) {
                    AppState.DetailSuccess(convertDtoToModel(serverResponse))
                } else {

                    AppState.Error(Throwable(response.errorBody().toString()))
                }) as AppState?
            )
        }

        override fun onFailure(call: Call<List<CharacterDTO>>, textError: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(textError.message)))
        }

        private fun convertDtoToModel(charDTO: List<CharacterDTO>): Character {
            return Character(
                charDTO[0].char_id,
                charDTO[0].name,
                charDTO[0].birthday,
                charDTO[0].occupation,
                charDTO[0].img,
                charDTO[0].status,
                charDTO[0].appearance,
                charDTO[0].nickname,
                charDTO[0].portrayed
            )
        }
    }

}