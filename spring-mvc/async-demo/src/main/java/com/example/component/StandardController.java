package com.example.component;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/standard")
public class StandardController {

	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestParam(defaultValue = "0") long waitSec, Model model) {

		Console.println("Start get.");

		model.addAttribute("acceptedTime", LocalDateTime.now());

		heavyProcessing(waitSec, model);

		Console.println("End get.");

		return "complete";
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

	@ExceptionHandler(Exception.class)
	public String handleException() {
		return "error";
	}

}
