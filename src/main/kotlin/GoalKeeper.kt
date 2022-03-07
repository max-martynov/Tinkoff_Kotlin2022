class GoalKeeper(
    name: String,
    age: Int,
    country: String) : FootballPlayer(name, age, country) {

    var saves: Int = 0
        private set

    override fun calculateTransferCost(): Int {
        if (country == "Spain")
            return super.calculateTransferCost() * 10
        return super.calculateTransferCost()
    }

    override fun calculateUsefulness(): Int {
        return saves
    }

    fun incrementSaves() {
        saves++
    }

}
