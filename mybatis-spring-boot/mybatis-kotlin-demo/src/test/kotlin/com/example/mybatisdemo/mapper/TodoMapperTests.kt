package com.example.mybatisdemo.mapper

import com.example.mybatisdemo.domain.Todo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.test.context.junit4.SpringRunner

import org.assertj.core.api.Assertions.assertThat
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.support.GeneratedKeyHolder

@RunWith(SpringRunner::class)
@MybatisTest
class TodoMapperTests {

    @Autowired
    lateinit var todoMapper: TodoMapper

    @Autowired
    lateinit var jdbcOperations: NamedParameterJdbcOperations

    @Test
    fun insert() {
        // setup
        // none

        // perform test and assertions
        run {
            val newTodo = Todo()
            newTodo.title = "飲み会"
            newTodo.details = "銀座 19:00"
            todoMapper.insert(newTodo)

            val actualTodo = jdbcOperations.queryForObject("SELECT * FROM todo WHERE id = :id",
                MapSqlParameterSource("id", newTodo.id),
                BeanPropertyRowMapper(Todo::class.java))
            assertThat(actualTodo?.id).isEqualTo(newTodo.id)
            assertThat(actualTodo?.title).isEqualTo("飲み会")
            assertThat(actualTodo?.details).isEqualTo("銀座 19:00")
            assertThat(actualTodo?.finished).isEqualTo(false)
        }
    }

    @Test
    fun select() {
        // setup
        val newTodo = Todo()
        newTodo.title = "飲み会"
        newTodo.details = "銀座 19:00"

        val keyHolder = GeneratedKeyHolder()
        jdbcOperations.update(
            "INSERT INTO todo (title, details, finished) VALUES(:title, :details, :finished)",
            BeanPropertySqlParameterSource(newTodo), keyHolder)

        // perform test and assertions
        run {
            val actualTodo = todoMapper.select(keyHolder.key!!.toInt())

            assertThat(actualTodo.id).isEqualTo(keyHolder.key!!.toInt())
            assertThat(actualTodo.title).isEqualTo("飲み会")
            assertThat(actualTodo.details).isEqualTo("銀座 19:00")
            assertThat(actualTodo.finished).isEqualTo(false)
        }
    }

}
