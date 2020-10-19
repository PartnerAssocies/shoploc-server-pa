package com.pa.shoploc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class HelloWorldController {

	@GetMapping("/")
	public String helloWorld() {
		return "hello world !";
	}
	
}
