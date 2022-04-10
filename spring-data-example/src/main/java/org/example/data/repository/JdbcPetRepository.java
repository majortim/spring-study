package org.example.data.repository;

import org.example.data.domain.Pet;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Profile("jdbc")
@Primary
@Repository
public class JdbcPetRepository implements PetRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcPetRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Long count() {
        return Optional.ofNullable(namedParameterJdbcOperations.getJdbcOperations().queryForObject("SELECT COUNT(*) FROM pet", Long.class)).orElse(0L);
    }

    @Override
    public void deleteByName(String name) {
        Map<String, String> param = new HashMap<>();
        param.put("name", name);
        namedParameterJdbcOperations.update("DELETE FROM pet WHERE name = :name", param);
    }

    @Override
    public List<Pet> findByName(String name) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);

        return namedParameterJdbcOperations.query("SELECT id, name, owner, species, sex, birth, death FROM pet WHERE name = :name", param, getRowMapper());
    }

    @Override
    public Optional<Pet> findById(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);

        return Optional.of(namedParameterJdbcOperations.
                query("SELECT id, name, owner, species, sex, birth, death FROM pet WHERE id = :id"
                        , param, getRowMapper()))
                .filter(l -> !l.isEmpty()).map(l -> l.get(0));
    }

    @Override
    public Iterable<Pet> findAll() {
        return namedParameterJdbcOperations.query("SELECT id, name, owner, species, sex, birth, death FROM pet", getRowMapper());
    }

    @SuppressWarnings("all")
    @Override
    public void deleteAll() {
        namedParameterJdbcOperations.getJdbcOperations().update("DELETE FROM pet");
    }

    @Override
    public void deleteAll(Iterable<Pet> iterable) {
        iterable.forEach(p -> deleteById(p.getId()));
    }


    @Override
    public void deleteById(Long id) {
        namedParameterJdbcOperations.getJdbcOperations().update("DELETE FROM pet WHERE id = ?", id);
    }
    @Override
    @SuppressWarnings("unchecked")
    public Pet save(Pet pet) {

        SqlParameterSource param = new BeanPropertySqlParameterSource(pet) {

            @Override
            public Object getValue(String paramName) throws IllegalArgumentException {
                Object value = super.getValue(paramName);
                if (value instanceof Enum) {
                    return value.toString();
                }

                return value;
            }
        };

        int updated;

        if(pet.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            updated = namedParameterJdbcOperations.update("INSERT INTO pet (NAME, OWNER, SPECIES, SEX, BIRTH, DEATH) VALUES(:name, :owner, :species, :sex, :birth, :death)", param, keyHolder);

            return (updated != 0) ? pet.withId(keyHolder.getKeyAs(Long.class)) : null;
        }

        updated = namedParameterJdbcOperations.update("UPDATE pet SET name = :name, owner = :owner, species = :species" +
                ", sex = :sex, birth = :birth, death  = :death WHERE id = :id", param);

        return (updated != 0) ?  pet : null;
    }

    public RowMapper<Pet> getRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String owner = rs.getString("owner");
            String species = rs.getString("species");
            Pet.Sex sex = Pet.Sex.valueOf(rs.getString("sex"));

            LocalDate birth = rs.getObject("birth", LocalDate.class);
            LocalDate death = rs.getObject("death", LocalDate.class);

            return Pet.builder().id(id).name(name).owner(owner).species(species).sex(sex).birth(birth).death(death).buildWithId();
        };
    }
}
