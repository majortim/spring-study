package org.example.springdata.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcPetRepository implements PetRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcPetRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public List<Pet> findAllByName(String name) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);

        return namedParameterJdbcOperations
                .query("SELECT name, owner, species, sex, birth, death FROM pet WHERE name = :name", param, getRowMapper());
    }

    @Override
    public List<Pet> findAll() {
        return namedParameterJdbcOperations.query("SELECT name, owner, species, sex, birth, death FROM pet", getRowMapper());
}

    @Override
    public void deleteAll() {
        namedParameterJdbcOperations.getJdbcOperations().update("DELETE FROM pet");
    }

    @Override
    public Pet save(Pet pet) {
        try{
            SqlParameterSource param = new BeanPropertySqlParameterSource(pet);
            namedParameterJdbcOperations.update("INSERT INTO pet (NAME, OWNER, SPECIES, SEX, BIRTH, DEATH) VALUES(:name, :owner, :species, :sex, :birth, :death)", param);
        }
        catch (RuntimeException e){
            logger.error("save error", e);
            return null;
        }
        return pet;
    }

    public RowMapper<Pet> getRowMapper() {
        return new RowMapper<Pet>() {
            public Pet mapRow(ResultSet rs, int rowNum) throws SQLException {

                String name = rs.getString("name");
                String owner = rs.getString("owner");
                String species = rs.getString("species");
                String sex = rs.getString("sex");

                LocalDate birth = rs.getObject("birth", LocalDate.class);
                LocalDate death = rs.getObject("death", LocalDate.class);

                return Pet.builder()
                        .name(name)
                        .owner(owner)
                        .species(species)
                        .sex(sex)
                        .birth(birth)
                        .death(death)
                        .build();
            }
        };
    }
}
