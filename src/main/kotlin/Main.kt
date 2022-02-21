fun main() {
    val forward1 = Forward("Cristiano Ronaldo", 38, "Portugal")
    val forward2 = Forward("Paulo Dybala", 28, "Argentina")

    forward1.incrementGoals()
    forward2.incrementGoals()

    val goalKeeper1 = GoalKeeper("Gianluigi Buffon", 44, "Italy")
    val goalKeeper2 =  GoalKeeper("Iker Casillas", 40, "Spain")

    goalKeeper1.incrementSaves()
    goalKeeper2.incrementSaves()

    val footballPlayers : List<FootballPlayer> = listOf(
        forward1, forward2, goalKeeper1, goalKeeper2
    )

    footballPlayers.forEach { footballPlayer ->
        footballPlayer.prettyPrintTransferCost()
    }

    val footballTeam = FootballTeam(footballPlayers)
    footballTeam.prettyPrintTotalCost()

    forward1.incrementGoals()
    footballTeam.prettyPrintTotalCost()
}
