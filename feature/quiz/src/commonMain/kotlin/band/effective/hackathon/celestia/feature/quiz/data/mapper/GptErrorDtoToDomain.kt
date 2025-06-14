package band.effective.hackathon.celestia.feature.quiz.data.mapper

import band.effective.hackathon.celestia.feature.quiz.data.model.GptErrorDto
import band.effective.hackathon.celestia.feature.quiz.data.model.RecommendedPlanetDto
import band.effective.hackathon.celestia.feature.quiz.domain.model.GptError
import band.effective.hackathon.celestia.feature.quiz.domain.model.RecommendedPlanet

class GptDomainMapper {

    fun map(dto: GptErrorDto) = GptError(dto.message)

    fun map(dto: RecommendedPlanetDto) = RecommendedPlanet(
        planetName = dto.planetName,
        description = dto.description,
        type = dto.type,
    )
}