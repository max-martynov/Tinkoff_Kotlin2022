class Forward(
    name: String,
    age: Int,
    country: String) : FootballPlayer(name, age, country) {

    private var goalsScored: Int = 0

    override fun calculateTransferCost(): Int {
        if (country == "Portugal")
            return super.calculateTransferCost() * 10
        return super.calculateTransferCost()
    }

    override fun calculateUsefulness(): Int {
        return goalsScored
    }

    fun incrementGoals() {
        goalsScored++
    }

}
