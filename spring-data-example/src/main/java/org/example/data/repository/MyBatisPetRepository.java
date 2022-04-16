package org.example.data.repository;

import org.apache.ibatis.session.SqlSession;
import org.example.data.domain.Pet;

import java.util.List;
import java.util.Optional;

//@Profile("mybatis")
//@Primary
//@Repository
public class MyBatisPetRepository implements PetRepository {
    private final SqlSession sqlSession;

    public MyBatisPetRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Long count() {
        return sqlSession.selectOne("count");
    }
    @Override
    public List<Pet> findAll() {
        return sqlSession.selectList("findAll");
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return Optional.ofNullable(sqlSession.selectOne("findById", id));
    }

    @Override
    public List<Pet> findByName(String name) {
        return sqlSession.selectList("findAllByPath-name", name);
    }


    @Override
    public void deleteAll() {
        sqlSession.delete("deleteAll");
    }

    @Override
    public void deleteAll(Iterable<Pet> iterable) {
        iterable.forEach(p -> deleteById(p.getId()));
    }

    @Override
    public void deleteByName(String name) {
        sqlSession.delete("deleteAll-name", name);
    }

    @Override
    public void deleteById(Long id) {
        sqlSession.delete("delete-id", id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Pet save(Pet pet) {
        int updated;

        if(pet.getId() == null) {
            updated = sqlSession.insert("insert", pet);
        }
        else
            updated = sqlSession.update("update", pet);

         return (updated > 0) ? pet : null;
    }
}
