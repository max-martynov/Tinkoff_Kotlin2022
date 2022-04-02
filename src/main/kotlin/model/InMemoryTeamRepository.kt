package model

class InMemoryTeamRepository : TeamRepository {

    private val teams = mutableSetOf<Team>()

    override fun addTeam(newTeam: Team) {
        teams.add(newTeam)
    }

    override fun getTeam(teamName: String): Team? {
        return teams.find { it.name == teamName }
    }
}