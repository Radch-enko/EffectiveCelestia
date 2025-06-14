package band.effective.hackathon.celestia.mapper

import band.effective.hackathon.celestia.core.domain.model.RecommendedPlanet
import band.effective.hackathon.celestia.navigation.NavRoutes

fun NavRoutes.Quiz.Planet.toRecommendedPlanet() = RecommendedPlanet(
    planetName = this.planetName,
    description = this.description,
    type = this.type,
)

fun RecommendedPlanet.toNavArgument() = NavRoutes.Quiz.Planet(
    planetName = this.planetName,
    description = this.description,
    type = this.type,
)