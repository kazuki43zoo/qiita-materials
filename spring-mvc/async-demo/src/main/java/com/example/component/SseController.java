package com.example.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sse")
public class SseController {

	private static final Logger logger = LoggerFactory.getLogger(SseController.class);

	@Autowired
	AsyncHelper asyncHelper;

	@RequestMapping(method = RequestMethod.GET)
	public SseEmitter sse(@RequestParam(defaultValue = "1") long eventNumber, @RequestParam(defaultValue = "0") long intervalSec) throws IOException {
		Console.println("Start get.");

		SseEmitter emitter = new SseEmitter();
		asyncHelper.sse(emitter, eventNumber, intervalSec);

		Console.println("End get.");
		return emitter;
	}

	@ExceptionHandler
	@ResponseBody
	public String handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e) {
		logger.error("timeout error is occurred.", e);
		return SseEmitter.event().data("timeout!!").build().stream()
				.map(d -> d.getData().toString())
				.collect(Collectors.joining());
	}

	@ExceptionHandler
	@ResponseBody
	public String handleException(Exception e) {
		logger.error("system error is occurred.", e);
		return SseEmitter.event().data("error !!").build().stream()
				.map(d -> d.getData().toString())
				.collect(Collectors.joining());
	}

}
