package org.example.springdata.domain;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public void deleteAll() {
        sqlSession.delete("deleteAll");
    }

    @Override
    public Pet save(Pet pet) {
        int updated = sqlSession.insert("save", pet);

        if(updated > 0)
            return pet;

        return null;
    }
}
