package util

import BodyType
import Vehicle

object TestData {

    val basicVehiclesWithDescription = listOf(
        VehicleWithDescription(
            vehicle = Vehicle("Tesla Model S", BodyType.Roadster, 100),
            description = "Roadster Tesla Model S costs ${100 * Constants.EXCHANGE_RATE}$"
        ),
        VehicleWithDescription(
            vehicle = Vehicle("Nissan Almera", BodyType.Sedan, 30),
            description = "Sedan Nissan Almera costs ${30 * Constants.EXCHANGE_RATE}$"
        ),
        VehicleWithDescription(
            vehicle = Vehicle("Toyota Sienna", BodyType.Minivan, 60),
            description = "Minivan Toyota Sienna costs ${60 * Constants.EXCHANGE_RATE}$"
        ),
        VehicleWithDescription(
            vehicle = Vehicle("Kia Sportage", BodyType.Crossover, 50),
            description = "Crossover Kia Sportage costs ${50 * Constants.EXCHANGE_RATE}$"
        )
    )

}