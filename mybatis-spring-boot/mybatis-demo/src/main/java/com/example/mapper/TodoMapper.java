package com.example.mapper;

import com.example.domain.Todo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TodoMapper {

    @Insert("INSERT INTO todo (title, details, finished) VALUES (#{title}, #{details}, #{finished})")
    @Options(useGeneratedKeys = true)
    void insert(Todo todo);

    @Select("SELECT id, title, details, finished FROM todo WHERE id = #{id}")
    Todo select(int id);

}