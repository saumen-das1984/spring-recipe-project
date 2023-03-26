package com.spring.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.recipe.command.IngredientCommand;
import com.spring.recipe.command.UnitOfMeasureCommand;
import com.spring.recipe.services.IngredientService;
import com.spring.recipe.services.RecipeService;
import com.spring.recipe.services.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Retrieve Ingredient List for recipe id "+ recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		return "recipe/ingredient/list";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Show Ingredient for recipe id "+ recipeId);
		log.debug("Show Ingredient for ingredient id "+ ingredientId);
		model.addAttribute("ingredient", ingredientService.findByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		model.addAttribute("uoms", ingredientService.findByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		return "recipe/ingredient/show";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
	public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Update Ingredient for recipe id "+ recipeId);
		log.debug("Update Ingredient for ingredient id "+ ingredientId);
		model.addAttribute("ingredient", ingredientService.findByRecipeIdandIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		model.addAttribute("uomList", unitOfMeasureService.listAllUOMs());
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping("/recipe/{recipeId}/ingredient")
	public String saveOrUpdateIngradient(@ModelAttribute IngredientCommand ingredientCommand) {
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);
		log.debug("Saved Ingredient for recipe id "+ savedCommand.getRecipeId());
		log.debug("Saved Ingredient for ingredient id "+ savedCommand.getId());
		
		return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public String createIngredient(@PathVariable String recipeId, Model model) {
		log.debug("Create Ingredient for recipe id "+ recipeId);
		IngredientCommand newIngredient = new IngredientCommand();
		newIngredient.setRecipeId(Long.valueOf(recipeId));
		model.addAttribute("ingredient", newIngredient);
		newIngredient.setUom(new UnitOfMeasureCommand());
		model.addAttribute("uomList", unitOfMeasureService.listAllUOMs());
		return "recipe/ingredient/ingredientform";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		log.debug("Deleted Ingredient for recipe id "+ recipeId);
		log.debug("Deleted Ingredient for ingredient id "+ ingredientId);
		ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		return "redirect:/recipe/"+recipeId+"/ingredients";
	}
}
