package com.example.mybatisdemo.mapper

import com.example.mybatisdemo.domain.Todo
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select

@Mapper
interface TodoMapper {

    @Insert("""
        INSERT INTO todo
            (title, details, finished)
        VALUES
            (#{title}, #{details}, #{finished})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insert(todo: Todo)

    @Select("""
        SELECT
            id, title, details, finished
        FROM
            todo
        WHERE
            id = #{id}
    """)
    fun select(id: Int): Todo

}