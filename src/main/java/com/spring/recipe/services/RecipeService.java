package com.spring.recipe.services;

import java.util.Set;

import com.spring.recipe.command.RecipeCommand;
import com.spring.recipe.domain.Recipe;

public interface RecipeService {
	
	Set<Recipe> getRecipes();
	public Recipe findById(Long l);
	RecipeCommand findCommandById(Long l);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	void deleteById(Long deletedId);
}
