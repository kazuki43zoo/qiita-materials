package com.example.mybatisdemo

import com.example.mybatisdemo.domain.Todo
import com.example.mybatisdemo.mapper.TodoMapper
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.Transactional

@SpringBootApplication
class MybatisKotlinDemoApplication (private val todoMapper: TodoMapper) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        val newTodo: Todo = Todo()
        newTodo.title = "飲み会"
        newTodo.details = "銀座 19:00"

        todoMapper.insert(newTodo) // 新しいTodoをインサートする

        val loadedTodo: Todo = todoMapper.select(newTodo.id) // インサートしたTodoを取得して標準出力する
        println("ID       : " + loadedTodo.id)
        println("TITLE    : " + loadedTodo.title)
        println("DETAILS  : " + loadedTodo.details)
        println("FINISHED : " + loadedTodo.finished)
    }

}

fun main(args: Array<String>) {
    runApplication<MybatisKotlinDemoApplication>(*args)
}
