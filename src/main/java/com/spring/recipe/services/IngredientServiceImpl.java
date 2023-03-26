package com.spring.recipe.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.recipe.command.IngredientCommand;
import com.spring.recipe.converters.IngredientCommandToIngredient;
import com.spring.recipe.converters.IngredientToIngredientCommand;
import com.spring.recipe.domain.Ingredient;
import com.spring.recipe.domain.Recipe;
import com.spring.recipe.repositories.RecipeRepository;
import com.spring.recipe.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {
	
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(IngredientCommandToIngredient ingredientCommandToIngredient,
			IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
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
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
				.findFirst();
		
		if(!ingredientOptional.isPresent()) {
			log.error("Ingredient Id not found - "+ingredientId);
		}

		return ingredientOptional.get();
	}


	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
		
		if(!recipeOptional.isPresent()) {
			log.error("Recipe Id not found - "+ingredientCommand.getRecipeId());
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
					.findFirst();
			
			if(ingredientOptional.isPresent()) {
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(ingredientCommand.getDescription());
				ingredientFound.setAmount(ingredientCommand.getAmount());
				ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
						.orElseThrow(() -> new RuntimeException("UOM IS NOT FOUND")));
			} else {
				//add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
			}
			
			Recipe savedRecipe = recipeRepository.save(recipe);
			
			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
					.findFirst();
			
			//check by description
			if(!savedIngredientOptional.isPresent()) {
				savedIngredientOptional = savedRecipe.getIngredients().stream()
						.filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
						.filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
						.filter(ingredient -> ingredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
						.findFirst();
						
			}
			
			return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
		}
	}


	@Override
	public void deleteById(Long recipeId, Long idToDelete) {
		log.debug("Deleting Ingredient for Id - "+idToDelete+" forr Recipe Id - "+recipeId);
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(recipeOptional.isPresent()) {
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(idToDelete))
					.findFirst();
			if(ingredientOptional.isPresent()) {
				Ingredient deletedIngredient = ingredientOptional.get();
				deletedIngredient.setRecipe(null);
				recipe.getIngredients().remove(deletedIngredient);
				recipeRepository.save(recipe);
			}
		} else {
			log.error("Recipe Id not found - "+recipeId);
		}
		
		
	}

}
