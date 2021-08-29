package org.example.springdata.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcPetRepository implements PetRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcPetRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long count() {
        return Optional.ofNullable(namedParameterJdbcOperations.getJdbcOperations().queryForObject("SELECT COUNT(*) FROM pet", Long.class)).orElse(0L);
    }

    @Override
    public void deleteById(Long id) {
        namedParameterJdbcOperations.getJdbcOperations().update("DELETE FROM pet WHERE id = ?", id);
    }

    @Override
    public void delete(Pet pet) {
        namedParameterJdbcOperations.update("DELETE FROM pet WHERE id = :id AND name = :name AND owner := owner", new BeanPropertySqlParameterSource(pet));
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> iterable) {
        for (Long id : iterable) {
            namedParameterJdbcOperations.getJdbcOperations().update("DELETE FROM pet WHERE id = ?", id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends Pet> iterable) {
        namedParameterJdbcOperations.getJdbcOperations().update("DELETE FROM pet");
    }

    @Override
    public void deleteByName(String name) {
        Map<String, String> param = new HashMap<>();
        param.put("name", name);
        namedParameterJdbcOperations.update("DELETE FROM pet WHERE name = :name", param);
    }

    @Override
    public List<Pet> findAllByName(String name) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);

        return namedParameterJdbcOperations
                .query("SELECT id, name, owner, species, sex, birth, death FROM pet WHERE name = :name", param, getRowMapper());
    }

    @Override
    public <S extends Pet> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Pet> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", id);

        List<Pet> list = namedParameterJdbcOperations.query("SELECT id, name, owner, species, sex, birth, death FROM pet WHERE id = :id", param, getRowMapper());
        int size = list.size();

        switch(size){
            case 1:
                return true;
            case 0:
                return false;
            default:
                throw new RuntimeException("Duplicate Id");
        }
    }

    @Override
    public
    Iterable<Pet> findAll() {
        return namedParameterJdbcOperations.query("SELECT id, name, owner, species, sex, birth, death FROM pet", getRowMapper());
}

    @Override
    public Iterable<Pet> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcOperations.getJdbcOperations().update("DELETE FROM pet");
    }

    @Override
    public <S extends Pet> S save(S pet) {
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
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String owner = rs.getString("owner");
            String species = rs.getString("species");
            String sex = rs.getString("sex");

            LocalDate birth = rs.getObject("birth", LocalDate.class);
            LocalDate death = rs.getObject("death", LocalDate.class);

            return Pet.builder()
                    .id(id)
                    .name(name)
                    .owner(owner)
                    .species(species)
                    .sex(sex)
                    .birth(birth)
                    .death(death)
                    .buildWithId();
        };
    }
}
