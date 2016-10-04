package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync // @Asyncの有効化
//@Configuration
public class AsyncConfig {
	@Bean
	public AsyncTaskExecutor taskExecutor() { // デフォルトだと"taskExecutor"という名前のBeanが利用される
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(10);
		return executor;
	}
}