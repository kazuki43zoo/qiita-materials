package com.example.component;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
public class AsyncHelper {

	@Async // 非同期実行するメソッドに@Asyncを付与する
	public void asyncProcessing(Model model, long waitSec, DeferredResult<String> deferredResult) {
		Console.println("Start Async processing.");

		if (waitSec == 999) {
			// 例外はスローではなく、setErrorResultメソッドを使用する
			// throw new IllegalStateException("Special parameter for confirm error.");
			deferredResult.setErrorResult(new IllegalStateException("Special parameter for confirm error."));
			return;
		}

		sleep(waitSec);

		model.addAttribute("completedTime", LocalDateTime.now());

		deferredResult.setResult("complete"); // 処理結果はDeferredResult#setResultメソッドを呼び出して設定する。

		Console.println("End Async processing.");
	}

	@Async // 非同期実行対象のメソッド
	public ListenableFuture<String> asyncProcessingForListenable(Model model, long waitSec) {
		Console.println("Start Async processing.");
		if (waitSec == 999) {
			throw new IllegalStateException("Special parameter for confirm error.");
		}

		sleep(waitSec);

		model.addAttribute("completedTime", LocalDateTime.now());

		Console.println("End Async processing.");

		return new AsyncResult<>("complete");
	}

	@Async // 非同期実行対象のメソッド
	public CompletableFuture<String> asyncProcessingForCompletable(Model model, long waitSec) {
		Console.println("Start Async processing.");
		if (waitSec == 999) {
			throw new IllegalStateException("Special parameter for confirm error.");
		}

		sleep(waitSec);

		model.addAttribute("completedTime", LocalDateTime.now());

		Console.println("End Async processing.");

		return CompletableFuture.completedFuture("complete");
	}

	@Async
	public CompletableFuture<ProcessingResult> asyncProcessing(ProcessingResult result, long waitSec) {
		Console.println("Start Async processing.");

		if (waitSec == 999) {
			throw new IllegalStateException("Special parameter for confirm error.");
		}

		sleep(waitSec);

		result.setCompletedTime(LocalDateTime.now());

		Console.println("End Async processing.");

		return CompletableFuture.completedFuture(result);
	}
	// ..

	@Async
	public void streaming(ResponseBodyEmitter emitter, long eventNumber, long intervalSec) throws IOException {
		Console.println("Start Async processing.");

		for (long i = 1; i <= eventNumber; i++) {
			sleep(intervalSec);
			emitter.send("msg" + i + "\r\n");
		}

		emitter.complete();

		Console.println("End Async processing.");
	}

	@Async
	public void sse(SseEmitter emitter, long eventNumber, long intervalSec) throws IOException {
		Console.println("Start Async processing.");

		if (intervalSec == 999) {
			try {
				throw new IllegalStateException("Special parameter for confirm error.");
				// ...
			} catch (Exception e) {
//				emitter.send("error !!");
//				emitter.complete();
				emitter.completeWithError(e);
				return;
			}
		}

		for (long i = 1; i <= eventNumber; i++) {
			sleep(intervalSec);
			emitter.send("msg" + i);
//			emitter.send(SseEmitter.event()
//					.comment("This is test event")
//					.id(UUID.randomUUID().toString())
//					.name("onlog")
//					.reconnectTime(3000)
//					.data("msg" + i));
		}

		emitter.complete();

		Console.println("End Async processing.");
	}

	private void sleep(long timeout) {
		try {
			TimeUnit.SECONDS.sleep(timeout);
		} catch (InterruptedException e) {
			Thread.interrupted();
		}
	}
}