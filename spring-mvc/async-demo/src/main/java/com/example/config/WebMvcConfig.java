package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	// ...
	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer.setTaskExecutor(mvcAsyncExecutor()); // スレッドプールを利用するようにカスタマイズ
		configurer.setDefaultTimeout(3000); // デフォルトのタイムアウト時間の指定
	}

	// ThreadPoolTaskExecutorはDIコンテナ上で管理するのがポイント
	// SpringのDIコンテナのライフサイクルに合わせて、適切なタイミングでinitializeとshutdownメソッドが呼び出される
	@Bean
	public AsyncTaskExecutor mvcAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(10);
		return executor;
	}

}