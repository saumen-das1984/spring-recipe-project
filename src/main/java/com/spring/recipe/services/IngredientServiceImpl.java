package com.spring.recipe.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.spring.recipe.command.IngredientCommand;
import com.spring.recipe.converters.IngredientToIngredientCommand;
import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {
	
	private final IngredientToIngredientCommand ingredientCommand;
	private final RecipeRepository recipeRepository;
	
	public IngredientServiceImpl(IngredientToIngredientCommand ingredientCommand, RecipeRepository recipeRepository) {
		this.ingredientCommand = ingredientCommand;
		this.recipeRepository = recipeRepository;
	}


	@Override
	public IngredientCommand findByRecipeIdandIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(!recipeOptional.isPresent()) {
			log.error("Recipe Id not found - "+recipeId);
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientCommand.convert(ingredient))
				.findFirst();
		
		if(!ingredientOptional.isPresent()) {
			log.error("Ingredient Id not found - "+ingredientId);
		}

		return ingredientOptional.get();
	}

}
