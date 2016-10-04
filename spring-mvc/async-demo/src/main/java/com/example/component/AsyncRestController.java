package com.example.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/api/async")
@RestController
public class AsyncRestController {
	@Autowired
	AsyncHelper asyncHelper;

	@RequestMapping(method = RequestMethod.GET)
	public CompletableFuture<ProcessingResult> get(@RequestParam(defaultValue = "0") long waitSec) {
		Console.println("Start get.");

		ProcessingResult result = new ProcessingResult();
		result.setAcceptedTime(LocalDateTime.now());

		CompletableFuture<ProcessingResult> future = asyncHelper.asyncProcessing(result, waitSec);

		Console.println("End get.");
		return future;
	}
}