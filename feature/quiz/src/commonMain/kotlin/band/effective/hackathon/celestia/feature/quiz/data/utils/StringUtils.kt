package band.effective.hackathon.celestia.feature.quiz.data.utils

import band.effective.hackathon.celestia.feature.quiz.data.model.GptMessage

fun GptMessage.toJsonString() = this.text.replace("```", "")