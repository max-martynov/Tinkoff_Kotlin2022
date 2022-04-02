package model

interface TeamRepository {

    fun addTeam(newTeam: Team)

    fun getTeam(teamName: String): Team?
}