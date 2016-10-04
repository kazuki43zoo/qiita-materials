package com.example.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/async")
public class AsyncController {

	@Autowired
	AsyncHelper asyncHelper;

	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> get(@RequestParam(defaultValue = "0") long waitSec, Model model) {

		Console.println("Start get.");

		model.addAttribute("acceptedTime", LocalDateTime.now());

		// Callableのcallメソッドの中に非同期処理を実装する
		// Callableは関数型インターフェースなのでJava SE 8+ならラムダ式が使えます。
		Callable<String> asyncProcessing = () -> {

			Console.println("Start Async processing.");

			heavyProcessing(waitSec, model);

			Console.println("End Async processing.");

			return "complete";
		};

		Console.println("End get.");

		return asyncProcessing;
	}

	private void heavyProcessing(long waitSec, Model model) {

		if (waitSec == 999) {
			throw new IllegalStateException("Special parameter for confirm error.");
		}

		try {
			TimeUnit.SECONDS.sleep(waitSec);
		} catch (InterruptedException e) {
			Thread.interrupted();
		}

		model.addAttribute("completedTime", LocalDateTime.now());

	}


	@RequestMapping(path = "deferred", method = RequestMethod.GET)
	public DeferredResult<String> getReferred(@RequestParam(defaultValue = "0") long waitSec, Model model) {

		Console.println("Start get.");

		model.addAttribute("acceptedTime", LocalDateTime.now());

		// 非同期処理を呼び出す。DeferredResultを非同期処理に引き渡すのがポイント。
		DeferredResult<String> deferredResult = new DeferredResult<>();
		asyncHelper.asyncProcessing(model, waitSec, deferredResult);

		Console.println("End get.");

		return deferredResult; // DeferredResultを返却する
	}

	@RequestMapping(path = "listenable", method = RequestMethod.GET)
	public ListenableFuture<String> getListenable(@RequestParam(defaultValue = "0") long waitSec, Model model) {

		Console.println("Start get.");

		model.addAttribute("acceptedTime", LocalDateTime.now());

		ListenableFuture<String> future = asyncHelper.asyncProcessingForListenable(model, waitSec);
		future.addCallback(
				Console::println, // 正常終了時のコールバック
				Console::println  // 例外発生時時のコールバック
		);

		Console.println("End get.");
		return future;
	}

	@RequestMapping(path = "completable", method = RequestMethod.GET)
	public CompletableFuture<String> getCompletable(@RequestParam(defaultValue = "0") long waitSec, Model model) {

		Console.println("Start get.");

		model.addAttribute("acceptedTime", LocalDateTime.now());

		CompletableFuture<String> future = asyncHelper.asyncProcessingForCompletable(model, waitSec);
		future.thenAccept(Console::println); // 正常終了時のコールバック

		Console.println("End get.");
		return future;
	}


	@ExceptionHandler
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public String handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e) {
		return "timeout";
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleException(Exception e) {
		return "error";
	}


//	@ExceptionHandler(Exception.class)
//	public String handleException() {
//		return "error";
//	}

}