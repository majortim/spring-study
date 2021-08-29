package org.example.springdata.domain;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("mybatis")
@Primary
@Repository
public class MyBatisPetRepository implements PetRepository{
    private SqlSession sqlSession;

    public MyBatisPetRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public long count() {
        return sqlSession.selectOne("count");
    }

    @Override
    public List<Pet> findAllByName(String name) {
        return sqlSession.selectList("findAllByName", name);
    }

    @Override
    public List<Pet> findAll() {
        return sqlSession.selectList("findAll");
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
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Pet> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Pet pet) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends Pet> iterable) {

    }

    @Override
    public void deleteAll() {
        sqlSession.delete("deleteAll");
    }

    @Override
    public <S extends Pet> S save(S pet) {
        int updated = sqlSession.insert("save", pet);

        if(updated > 0)
            return pet;

        return null;
    }

    @Override
    public void deleteByName(String name) {

    }
}
