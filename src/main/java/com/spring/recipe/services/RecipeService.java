package com.spring.recipe.services;

import java.util.Set;

import com.spring.recipe.domain.Recipe;

public interface RecipeService {
	
	Set<Recipe> getRecipes();
	
}
