package com.spring.recipe.services;

import com.spring.recipe.command.IngredientCommand;

public interface IngredientService {
	public IngredientCommand findByRecipeIdandIngredientId(Long recipeId, Long ingredientId);

}
