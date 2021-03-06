package com.pa.shoploc.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe d'accueil de l'api representant un hello world sur le '/'
 */
@RestController
@CrossOrigin("*")
public class HelloWorldController {

	@GetMapping("/")
	public String helloWorld() {
		return "hello world !";
	}
	
}
