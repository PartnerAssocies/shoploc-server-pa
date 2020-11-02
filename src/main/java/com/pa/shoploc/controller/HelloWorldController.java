package com.pa.shoploc.controller;

<<<<<<< HEAD
import org.springframework.stereotype.Controller;
=======
>>>>>>> dfa659aac134f8b9837f2437ca4cc7487d63234a
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
<<<<<<< HEAD
@CrossOrigin("*")
=======
@CrossOrigin
>>>>>>> dfa659aac134f8b9837f2437ca4cc7487d63234a
public class HelloWorldController {

	@GetMapping("/")
	public String helloWorld() {
		return "hello world !";
	}
	
}
