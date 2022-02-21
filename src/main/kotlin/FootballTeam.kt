class FootballTeam(private val players: List<FootballPlayer>) {

    fun calculateTotalCost(): Int {
        return players.foldRight(0) {footballPlayer, acc -> acc + footballPlayer.calculateTransferCost() }
    }

    fun prettyPrintTotalCost() {
        println("Team costs ${calculateTotalCost()}$.")
    }
}