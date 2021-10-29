package ru.princesch.testbbchars.viewmodel

import ru.princesch.testbbchars.model.Character
import ru.princesch.testbbchars.model.CharacterDTO

sealed class AppState {
    data class CharactersSuccess(val charactersData: List<Character>) : AppState()
    data class DetailSuccess(val detailData: Character) : AppState()
    class Error (val error : Throwable) : AppState()
    object Loading : AppState()
}
