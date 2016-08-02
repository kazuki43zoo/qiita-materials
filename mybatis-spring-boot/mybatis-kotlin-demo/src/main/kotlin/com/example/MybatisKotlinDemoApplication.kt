package com.example

import com.example.domain.Todo
import com.example.mapper.TodoMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.Transactional

@SpringBootApplication
open class MybatisKotlinDemoApplication : CommandLineRunner {

    @Autowired
    lateinit var todoMapper: TodoMapper

    @Transactional
    override fun run(vararg args: String?) {
        var newTodo: Todo = Todo()
        newTodo.title = "飲み会"
        newTodo.details = "銀座 19:00"

        todoMapper.insert(newTodo) // 新しいTodoをインサートする

        var loadedTodo: Todo = todoMapper.select(newTodo.id) // インサートしたTodoを取得して標準出力する
        println("ID       : " + loadedTodo.id)
        println("TITLE    : " + loadedTodo.title)
        println("DETAILS  : " + loadedTodo.details)
        println("FINISHED : " + loadedTodo.finished)
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(MybatisKotlinDemoApplication::class.java, *args)
}
