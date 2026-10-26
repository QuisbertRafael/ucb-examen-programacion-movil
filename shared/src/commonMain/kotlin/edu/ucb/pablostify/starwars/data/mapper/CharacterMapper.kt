package edu.ucb.pablostify.starwars.data.mapper

import edu.ucb.pablostify.starwars.data.dto.CharacterDto
import edu.ucb.pablostify.starwars.domain.model.Character

fun CharacterDto.toModel(): Character {
    return Character(
        name = name,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        gender = gender
    )
}
