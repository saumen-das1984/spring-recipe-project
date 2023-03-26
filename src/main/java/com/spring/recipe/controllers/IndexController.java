package com.spring.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.recipe.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {
	
	private RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping({"","/","index", "index.html"})
	public String getIndexPage(Model model) {
		log.debug("Controller Called started ....");
		model.addAttribute("recipes", recipeService.getRecipes());
		
		return "index";
	}

}
