package ru.princesch.testbbchars.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val char_id: Int = 1,
    val name: String = "Walter White",
    val birthday: String = "09-07-1958",
    val occupation: List<String> = listOf("High School Chemistry Teacher", "Meth King Pin"),
    val img: String = "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
    val status: String = "Deceased",
    val appearance: List<Int>? = null,
    val nickname: String? = "Heisenberg",
    val portrayed: String = "Bryan Cranston"
    ): Parcelable