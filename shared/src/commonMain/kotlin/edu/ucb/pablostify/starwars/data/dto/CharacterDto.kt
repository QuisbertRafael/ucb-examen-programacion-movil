package edu.ucb.pablostify.starwars.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//En la creacion del characterDto seguramente hubiera sido un poco más factible el realizarlo
//como en la clase pasada en tenerlas separadas y no todas juntas, se hizo de dicha forma para
//asi no tener tantos archivos

@Serializable
data class PeopleListDto(
    val results: List<CharacterDto>
)

@Serializable
data class CharacterDto(
    val name: String,
    val height: String,
    val mass: String,
    @SerialName("hair_color") val hairColor: String,
    @SerialName("skin_color") val skinColor: String,
    @SerialName("eye_color") val eyeColor: String,
    val gender: String
)
