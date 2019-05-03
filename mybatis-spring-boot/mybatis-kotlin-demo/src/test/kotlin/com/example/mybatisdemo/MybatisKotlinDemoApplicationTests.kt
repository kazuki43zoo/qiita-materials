package com.example.mybatisdemo

import org.hamcrest.Matchers.containsString
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.rule.OutputCapture
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class MybatisKotlinDemoApplicationTests {

    companion object {
        @ClassRule @JvmField val out = OutputCapture()
    }

    @Test
    fun contextLoads() {
        out.expect(containsString("ID       : 1"))
        out.expect(containsString("TITLE    : 飲み会"))
        out.expect(containsString("DETAILS  : 銀座 19:00"))
        out.expect(containsString("FINISHED : false"))
    }

}
