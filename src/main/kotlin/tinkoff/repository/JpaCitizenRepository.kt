package tinkoff.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tinkoff.model.Citizen

@Repository
interface JpaCitizenRepository : JpaRepository<Citizen, Int>