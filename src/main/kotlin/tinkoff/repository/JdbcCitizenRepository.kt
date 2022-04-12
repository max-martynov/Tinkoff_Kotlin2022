package tinkoff.repository

import org.springframework.context.annotation.Primary
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import tinkoff.model.Citizen
import tinkoff.model.CitizenRepository
import java.sql.ResultSet


//@Primary
@Service
class JdbcCitizenRepository(private val jdbcTemplate: JdbcTemplate): CitizenRepository {

    private val mapper: RowMapper<Citizen> = RowMapper<Citizen> { resultSet: ResultSet, _: Int ->
        Citizen(resultSet.getInt("personal_id"), resultSet.getString("crime_history"))
    }

    override fun addCitizen(citizen: Citizen) {
        jdbcTemplate.update("insert into citizens(personal_id, crime_history) values(?, ?)", citizen.personalId, citizen.crimeHistory)
    }

    override fun getCitizen(id: Int): Citizen? {
       return try {
           jdbcTemplate.queryForObject("select * from citizens where personal_id=?", mapper, id)
       } catch (e: EmptyResultDataAccessException) {
           null
       }
    }

    override fun getPageOfCitizens(pageNumber: Int, pageSize: Int): List<Citizen> {
        val from = pageNumber * pageSize
        val to = (pageNumber + 1) * pageSize
        return jdbcTemplate.query("select * from citizens limit ? offset ?", mapper, to, from)
    }

    override fun clear() {
        jdbcTemplate.update("delete from citizens")
    }
}