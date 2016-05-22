package com.example;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.OutputCapture;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.containsString;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MybatisDemoApplication.class)
public class MybatisDemoApplicationTests {

    @ClassRule
    public static OutputCapture out = new OutputCapture(); // System.outの内容をキャプチャする

    @Test
    public void contextLoads() {
        // System.outした内容を検証する
        out.expect(containsString("ID       : 1"));
        out.expect(containsString("TITLE    : 飲み会"));
        out.expect(containsString("DETAILS  : 銀座 19:00"));
        out.expect(containsString("FINISHED : false"));
    }
}