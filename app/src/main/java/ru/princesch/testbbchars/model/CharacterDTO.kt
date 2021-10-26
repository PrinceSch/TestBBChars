package ru.princesch.testbbchars.model

data class CharacterDTO(
    val char_id: Int,
    val name: String,
    val birthday: String,
    val occupation: List<String>,
    val img: String,
    val status: String,
    val appearance: List<Int>,
    val nickname: String?,
    val portrayed: String
)