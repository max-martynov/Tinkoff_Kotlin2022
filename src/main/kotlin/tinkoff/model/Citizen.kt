package tinkoff.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

data class Citizen(
    val personalId: Int,
    val crimeHistory: String
)